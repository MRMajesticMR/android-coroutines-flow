package com.majestic_dev.coroutinesflow.ui.main

import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.majestic_dev.coroutinesflow.R
import com.majestic_dev.coroutinesflow.base.ViewBindingSupportFragment
import com.majestic_dev.coroutinesflow.databinding.SMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainScreen : ViewBindingSupportFragment<SMainBinding>(R.layout.s_main) {

    private val viewModel: ViewModel by viewModels()

    private var singleEventsJob: Job? = null

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

    override fun onStart() {
        super.onStart()

        singleEventsJob = viewModel.eventsFlow
            .onEach {
                when (it) {
                    is Event.ShowToast -> {
                        Toast.makeText(requireContext(), it.text, Toast.LENGTH_SHORT).show()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onStop() {
        singleEventsJob?.cancel()

        super.onStop()
    }

}