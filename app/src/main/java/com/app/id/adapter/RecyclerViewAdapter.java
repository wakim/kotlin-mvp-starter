package com.app.id.adapter;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.id.R;
import com.app.id.view.AbstractView;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewAdapter<M extends Parcelable, V extends AbstractView<M>> extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder<V>> {

    private static final int LOADING_TYPE = 0;
    private static final int ITEM_TYPE = 1;

    LayoutInflater inflater;

    ArrayList<M> list = new ArrayList<>();

    boolean loading = false;

    public RecyclerViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOADING_TYPE) {
            return new RecyclerViewHolder<>(inflater.inflate(R.layout.list_item_loading, parent, false));
        } else {
            RecyclerViewHolder<V> holder = new RecyclerViewHolder<>(inflater.inflate(getLayoutResForViewType(viewType), parent, false));

            onPostCreateViewHolder(holder, parent);

            return holder;
        }
    }

    void onPostCreateViewHolder(RecyclerViewHolder<V> holder, ViewGroup parent) { }

    @LayoutRes
    abstract int getLayoutResForViewType(int viewType);

    @Override
    public void onBindViewHolder(RecyclerViewHolder<V> holder, int position) {
        if (holder.getItemViewType() == LOADING_TYPE) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();

            if (list == null || list.size() == 0) {
                lp.height = lp.width = RecyclerView.LayoutParams.MATCH_PARENT;
            } else {
                lp.height = lp.width = RecyclerView.LayoutParams.WRAP_CONTENT;
            }
        } else {
            M m = list.get(position);

            bind(holder.get(), m);

            onPostBindViewHolder(holder, m, position);
        }
    }

    public M getLastItem() {
        if (list.isEmpty()) {
            return null;
        }

        return list.get(list.size() - 1);
    }

    public M getFirstItem() {
        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    void bind(V v, M m) {
        v.bind(m);
    }

    void onPostBindViewHolder(RecyclerViewHolder<V> holder, M m, int position) { }

    @Override
    public int getItemViewType(int position) {
        if (loading && position == (getItemCount() - 1)) {
            return LOADING_TYPE;
        }

        return getViewTypeForPosition(position);
    }

    int getViewTypeForPosition(int position) {
        return ITEM_TYPE;
    }

    public void update(M m) {
        int indexOf = list.indexOf(m);

        if (indexOf > -1) {
            notifyItemChanged(indexOf);
        }
    }

    public void setLoading(boolean loading) {
        boolean old = this.loading;

        this.loading = loading;

        if (old != loading) {
            int size = list == null ? 0 : list.size();

            if (loading)
                notifyItemInserted(size);
            else
                notifyItemRemoved(size);
        }
    }

    public boolean isLoading() {
        return loading;
    }

    public int getCount() {
        return list == null ? 0 : list.size();
    }

    public void clear() {
        int previousSize = list.size();

        list.clear();
        notifyItemRangeRemoved(0, previousSize);
    }

    public List<M> getList() {
        return list;
    }

    public void addAll(@NonNull List<M> list) {
        int previous = this.list.size();

        this.list.addAll(list);

        notifyItemRangeInserted(previous, list.size());
    }

    @Override
    public int getItemCount() {
        int count = getCount();

        if (loading) {
            count++;
        }

        return count;
    }

    public void onRestoreState(@NonNull Parcelable savedInstanceState) {
        if (savedInstanceState instanceof SavedState) {
            //noinspection unchecked
            SavedState<M> state = (SavedState<M>) savedInstanceState;

            list = state.list;
        }
    }

    public ArrayList<M> getItems() {
        return list;
    }

    public Parcelable onSaveInstanceState() {
        return new SavedState<>(list);
    }

    public static class SavedState<M extends Parcelable> implements Parcelable {
        public ArrayList<M> list;

        public SavedState(ArrayList<M> list) {
            this.list = list;
        }

        protected SavedState(Parcel in) {
            //noinspection unchecked
            list = in.readArrayList(RecyclerViewAdapter.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(list);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    public static class RecyclerViewHolder<V> extends RecyclerView.ViewHolder {
        public RecyclerViewHolder(View itemView) {
            super(itemView);
        }

        public V get() {
            //noinspection unchecked
            return (V) itemView;
        }
    }
}
