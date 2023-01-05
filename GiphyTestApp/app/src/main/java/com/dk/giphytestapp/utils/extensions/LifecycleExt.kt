package com.dk.giphytestapp.utils.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Fragment.observe(flow: (Flow<T?>)?, block: suspend (t: T) -> Unit) {
    flow ?: return
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect {
                it?.also {
                    block(it)
                }
            }
        }
    }
}
