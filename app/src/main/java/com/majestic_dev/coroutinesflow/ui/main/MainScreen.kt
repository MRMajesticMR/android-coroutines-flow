package com.majestic_dev.coroutinesflow.ui.main

import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import com.majestic_dev.coroutinesflow.R
import com.majestic_dev.coroutinesflow.base.ViewBindingSupportFragment
import com.majestic_dev.coroutinesflow.databinding.SMainBinding

class MainScreen : ViewBindingSupportFragment<SMainBinding>(R.layout.s_main) {

    private val viewModel: ViewModel by viewModels()

    override fun bind(view: View): SMainBinding =
        SMainBinding.bind(view)

    override fun initView(view: View) {
        viewModel.visible.observe(viewLifecycleOwner) {
            binding?.tvText?.isGone = !it
        }

        binding?.bToggle?.setOnClickListener { viewModel.toggleVisibility() }
    }

}