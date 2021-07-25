package com.majestic_dev.coroutinesflow.ui.main

import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.majestic_dev.coroutinesflow.R
import com.majestic_dev.coroutinesflow.base.ViewBindingSupportFragment
import com.majestic_dev.coroutinesflow.databinding.SMainBinding
import kotlinx.coroutines.flow.collect

class MainScreen : ViewBindingSupportFragment<SMainBinding>(R.layout.s_main) {

    private val viewModel: ViewModel by viewModels()

    override fun bind(view: View): SMainBinding =
        SMainBinding.bind(view)

    override fun initView(view: View) {
        lifecycleScope.launchWhenStarted {
            viewModel.visible.collect { binding?.tvText?.isGone = !it }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.message.collect { binding?.tvText?.text = it }
        }

        binding?.bToggle?.setOnClickListener {
            viewModel.changeMessage("Message from screen")
            viewModel.toggleVisibility()
        }
    }

}