package com.majestic_dev.coroutinesflow.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBindingSupportFragment<B : ViewBinding>(@LayoutRes layoutId: Int) :
    Fragment(layoutId) {

    protected var binding: B? = null

    protected abstract fun bind(view: View): B

    protected open fun initView(view: View) {
        //.
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = bind(view)

        initView(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

}