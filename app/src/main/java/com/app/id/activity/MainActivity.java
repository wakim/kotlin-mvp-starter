package com.app.id.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.app.id.R;
import com.app.id.controller.ApiController;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Inject
    ApiController apiController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);
    }
}
