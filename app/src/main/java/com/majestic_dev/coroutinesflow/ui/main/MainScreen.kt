package com.majestic_dev.coroutinesflow.ui.main

import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.majestic_dev.coroutinesflow.R
import com.majestic_dev.coroutinesflow.base.ViewBindingSupportFragment
import com.majestic_dev.coroutinesflow.databinding.SMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventsFlow.collect {
                    when (it) {
                        is Event.ShowToast -> {
                            Toast.makeText(requireContext(), it.text, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding?.bToggle?.setOnClickListener {
            viewModel.changeMessage("Message from screen")
            viewModel.toggleVisibility()
        }
    }

}