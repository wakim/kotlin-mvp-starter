package com.app.id.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.id.R
import com.app.id.extensions.createParcel
import com.app.id.view.AbstractView
import java.util.*

abstract class RecyclerViewAdapter<M : Parcelable, V : AbstractView<M>>(context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder<V>>() {

    internal var inflater: LayoutInflater

    var items: ArrayList<M> = ArrayList()
        internal set

    var isLoading = false
        set (loading) {
            val old = this.isLoading

            field = loading

            if (old != loading) {
                val size = items.size

                if (loading)
                    notifyItemInserted(size)
                else
                    notifyItemRemoved(size)
            }
        }

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder<V> {
        if (viewType == LOADING_TYPE) {
            return RecyclerViewHolder(inflater.inflate(R.layout.list_item_loading, parent, false))
        } else {
            return RecyclerViewHolder<V>(inflater.inflate(getLayoutResForViewType(viewType), parent, false)).apply {
                onPostCreateViewHolder(this, parent)
            }
        }
    }

    internal fun onPostCreateViewHolder(holder: RecyclerViewHolder<V>, parent: ViewGroup) { }

    @LayoutRes
    internal abstract fun getLayoutResForViewType(viewType: Int): Int

    override fun onBindViewHolder(holder: RecyclerViewHolder<V>, position: Int) {
        if (holder.itemViewType == LOADING_TYPE) {
            val lp = holder.itemView.layoutParams as RecyclerView.LayoutParams

            with (lp) {
                if (items.size == 0) {
                    height = RecyclerView.LayoutParams.MATCH_PARENT
                    width = RecyclerView.LayoutParams.MATCH_PARENT
                } else {
                    height = RecyclerView.LayoutParams.WRAP_CONTENT
                    width = RecyclerView.LayoutParams.WRAP_CONTENT
                }
            }
        } else {
            val m = items[position]

            bind(holder.get(), m)

            onPostBindViewHolder(holder, m, position)
        }
    }

    val lastItem: M?
        get() = items.lastOrNull()

    val firstItem: M?
        get() = items.firstOrNull()

    internal fun bind(v: V, m: M) {
        v.bind(m)
    }

    internal fun onPostBindViewHolder(holder: RecyclerViewHolder<V>, m: M, position: Int) { }

    override fun getItemViewType(position: Int): Int {
        if (isLoading && position == itemCount - 1) {
            return LOADING_TYPE
        }

        return getViewTypeForPosition(position)
    }

    internal fun getViewTypeForPosition(position: Int): Int = ITEM_TYPE

    fun update(m: M) {
        val indexOf = items.indexOf(m)

        if (indexOf > -1) {
            notifyItemChanged(indexOf)
        }
    }

    val count: Int
        get() = items.size

    fun clear() {
        val previousSize = items.size

        items.clear()
        notifyItemRangeRemoved(0, previousSize)
    }

    fun addAll(list: List<M>) {
        val previous = this.items.size

        this.items.addAll(list)

        notifyItemRangeInserted(previous, list.size)
    }

    override fun getItemCount(): Int {
        var count = count

        if (isLoading) {
            count++
        }

        return count
    }

    @Suppress("UNCHECKED_CAST")
    fun onRestoreState(savedInstanceState: Parcelable) {
        if (savedInstanceState is SavedState<*>) {
            items = savedInstanceState.let { it.list as? ArrayList<M>? } ?: ArrayList()
        }
    }

    fun onSaveInstanceState() = SavedState(items)

    data class SavedState<M: Parcelable> (var list: ArrayList<M>): Parcelable {
        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeTypedList(list)
        }

        override fun describeContents(): Int = 0

        protected constructor(parcelIn: Parcel): this(parcelIn.createTypedArrayList(SavedState.CREATOR) as? ArrayList<M> ?: ArrayList<M>())

        companion object {
            @JvmField @Suppress("unused")
            val CREATOR = createParcel { SavedState<Parcelable>(it) }
        }
    }

    class RecyclerViewHolder<V>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @Suppress("UNCHECKED_CAST")
        fun get(): V = itemView as V
    }

    companion object {

        private val LOADING_TYPE = 0
        private val ITEM_TYPE = 1
    }
}
