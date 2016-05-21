package com.app.id.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.security.keystore.UserNotAuthenticatedException;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.app.id.R;
import com.app.id.application.AppLog;
import com.app.id.application.Application;
import com.app.id.bus.GenericPublishSubject;
import com.app.id.bus.PublishItem;
import com.app.id.dagger.ActivityComponent;
import com.app.id.dagger.Injector;
import com.app.id.dagger.modules.ActivityModule;
import com.app.id.exception.NetworkConnectivityException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity {

    public static final String PARENT_EXTRA = "PARENT_EXTRA";

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    private Unbinder baseUnbider;
    protected Unbinder unbinder;

    @Inject
    Application app;

    @BindView(R.id.coordinator_layout)
    @Nullable
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)
    @Nullable
    Toolbar toolbar;

    ActivityComponent activityComponent;

    AlertDialog loadingDialog;

    boolean stopped = false;

    boolean dialogVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = Injector.obtainAppComponent(this).plus(new ActivityModule(this));
        activityComponent.inject(this);

        bindConnectivityPublishSubject();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        baseUnbider = ButterKnife.bind(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getIntent().hasExtra(PARENT_EXTRA)) {
            supportFinishAfterTransition();
            return true;
        }

        Intent parentIntent = NavUtils.getParentActivityIntent(this);

        if (parentIntent == null) {
            supportFinishAfterTransition();
            return true;
        }

        if (NavUtils.shouldUpRecreateTask(this, parentIntent)) {
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(parentIntent)
                    .startActivities();

            supportFinishAfterTransition();
            return true;
        } else {
            startActivity(parentIntent);
            supportFinishAfterTransition();

            return true;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (Injector.matchesActivityComponentService(name)) {
            return activityComponent;
        }

        return super.getSystemService(name);
    }

    void bindConnectivityPublishSubject() {
        Subscription subscription = GenericPublishSubject.PUBLISH_SUBJECT
                .filter(new Func1<PublishItem, Boolean>() {
                    @Override
                    public Boolean call(PublishItem publishItem) {
                        return publishItem.type == GenericPublishSubject.CONNECTIVITY_CHANGE_TYPE;
                    }
                })
                .subscribe(new Action1<PublishItem>() {
                    @Override
                    public void call(PublishItem publishItem) {
                        onConnectivityChanged((Boolean) (publishItem.object));
                    }
                });

        addSubscription(subscription);
    }

    void onConnectivityChanged(boolean connected) { }

    protected void addSubscription(@Nullable Subscription subscription) {
        if (subscription == null) {
            return;
        }

        getCompositeSubscription().add(subscription);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }

        unbinder.unbind();
        baseUnbider.unbind();

        app.onForegroundActivityDestroy(this);

        app.watch(this);
    }

    @Override
    protected void onResume() {
        stopped = false;
        super.onResume();
        app.onForegroundActivityResume(this);
    }

    @Override
    protected void onStop() {
        stopped = true;
        super.onStop();
    }

    protected void snack(@StringRes int messageResId, @Snackbar.Duration int duration) {
        View view = coordinatorLayout == null ? findViewById(android.R.id.content) : coordinatorLayout;

        if (view != null) {
            Snackbar.make(view, messageResId, duration).show();
        }
    }

    public void toast(Throwable error) {
        Toast.makeText(this, getErrorMessage(error), Toast.LENGTH_LONG).show();
    }

    public void snack(Throwable error) {
        snack(getErrorMessage(error), Snackbar.LENGTH_LONG);
    }

    @StringRes
    protected int getErrorMessage(Throwable error) {
        AppLog.e(error);

        if (isNetworkError(error)) {
            return R.string.no_connectivity;
        } else if (error instanceof UserNotAuthenticatedException) {
            return R.string.not_logged;
        } else {
            return R.string.unknown_error;
        }
    }

    boolean isNetworkError(Throwable t) {
        return t instanceof NetworkConnectivityException
                || t instanceof UnknownHostException
                || t instanceof SocketTimeoutException;
    }

    protected CompositeSubscription getCompositeSubscription() {
        if (compositeSubscription == null || compositeSubscription.isUnsubscribed()) {
            compositeSubscription = new CompositeSubscription();
        }

        return compositeSubscription;
    }

    public void showLoading() {
        hideLoading();
        dialogVisible = true;

        buildNewLoadingDialog().show();
    }

    public void hideLoading() {
        if (isDialogShowing()) {
            dialogVisible = false;

            try {
                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                }
            } catch (IllegalArgumentException ignored) { }
        }
    }

    boolean isDialogShowing() {
        return dialogVisible;
    }

    AlertDialog buildNewLoadingDialog() {
        loadingDialog = new AlertDialog.Builder(this)
                .setView(R.layout.dialog_loading)
                .setCancelable(false)
                .create();

        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return loadingDialog;
    }

    public boolean isActive() {
        return !stopped;
    }
}
