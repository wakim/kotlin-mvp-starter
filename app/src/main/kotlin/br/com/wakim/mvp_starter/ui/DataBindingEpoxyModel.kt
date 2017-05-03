package br.com.wakim.mvp_starter.ui

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyModelWithView

class DataBindModel<out T>(val layoutResId: Int, val variableId: Int, val model: T) : EpoxyModelWithView<View>() {

    var binding: ViewDataBinding? = null
    var layoutInflater: LayoutInflater? = null

    override fun buildView(parent: ViewGroup?): View {
        val li = layoutInflater ?: LayoutInflater.from(parent?.context).apply { layoutInflater = this }

        binding = DataBindingUtil.inflate(li, layoutResId, parent, false)

        return binding?.root!!
    }

    override fun bind(view: View?) {
        super.bind(view)

        binding?.let {
            it.setVariable(variableId, model)
            it.executePendingBindings()
        }
    }
}