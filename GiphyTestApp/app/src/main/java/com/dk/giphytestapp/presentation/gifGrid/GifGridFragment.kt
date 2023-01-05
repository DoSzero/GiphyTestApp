package com.dk.giphytestapp.presentation.gifGrid

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.dk.giphytestapp.R
import com.dk.giphytestapp.databinding.GifGridFragmentBinding
import com.google.android.material.snackbar.Snackbar
import com.dk.giphytestapp.presentation.gifGrid.list.GifsPagingAdapter
import com.dk.giphytestapp.utils.extensions.observe
import com.dk.giphytestapp.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifGridFragment : Fragment(R.layout.gif_grid_fragment) {

    private val viewModel: GifGridViewModel by viewModels()

    private val adapter by lazy {
        GifsPagingAdapter(viewModel::onGifItemClick, viewModel::onGifItemLongClick)
    }

    private val binding by viewBinding(GifGridFragmentBinding::bind)

    private val errorSnackBar by lazy {
        Snackbar.make(
            requireContext(),
            binding.root,
            getString(R.string.generic_error_message),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(getString(R.string.retry_loading_action)) {
                adapter.refresh()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.photoGrid.adapter = adapter

        observeState()
        setListeners()
    }

    private fun setListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }

        binding.searchInputEditText.doAfterTextChanged {
            viewModel.performSearch(it.toString())
        }
    }

    private fun observeState() {
        observe(viewModel.gifsFlow) { gifs ->
            adapter.submitData(gifs)
        }

        observe(adapter.loadStateFlow) { loadState ->
            when (loadState.refresh) {
                is LoadState.NotLoading -> {
                    binding.swipeRefresh.isRefreshing = false
                    setPlaceholderVisibility()
                    errorSnackBar.dismiss()
                }
                LoadState.Loading -> {
                    showRefreshIfNotShown()
                    errorSnackBar.dismiss()
                    binding.nothingFoundText.isVisible = false
                }
                is LoadState.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    errorSnackBar.show()
                    setPlaceholderVisibility()
                }
            }
        }
    }

    private fun setPlaceholderVisibility() {
        binding.nothingFoundText.isVisible = adapter.itemCount == 0
    }

    private fun showRefreshIfNotShown() {
        if (!binding.swipeRefresh.isRefreshing) {
            binding.swipeRefresh.isRefreshing = true
        }
    }
}
