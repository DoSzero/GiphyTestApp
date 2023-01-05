package com.dk.giphytestapp.presentation.gifGrid

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dk.giphytestapp.domain.interactor.GifInteractor
import com.dk.giphytestapp.presentation.gifGrid.model.GifImageGridItemModel
import com.dk.giphytestapp.presentation.gifGrid.model.toItemModel
import com.dk.giphytestapp.utils.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GifGridViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: GifInteractor,
    private val navigationDispatcher: NavigationDispatcher
) : ViewModel() {

    private val clearList = Channel<Unit>(Channel.CONFLATED)
    private val searchTermFlow = savedStateHandle.getStateFlow(SEARCH_TERM_VALUE, "")
    private val searchTermInputFlow = MutableStateFlow("")
    private val searchTermInputDebouncesFlow = searchTermInputFlow
        .debounce(SEARCH_TIMEOUT)

    init {
        viewModelScope.launch {
            searchTermInputDebouncesFlow.collectLatest {
                savedStateHandle[SEARCH_TERM_VALUE] = it
                clearList.trySend(Unit)
            }
        }
    }

    val gifsFlow = flowOf(
        clearList.receiveAsFlow().map { PagingData.empty() },
        searchTermFlow.flatMapLatest {
            interactor.getPaging(it, true)
        }.map { pagingData ->
            pagingData.map { it.toItemModel() }
        }.cachedIn(viewModelScope)
    ).flattenMerge(2)

    fun performSearch(searchTerm: String) {
        searchTermInputFlow.value = searchTerm
    }

    fun onGifItemLongClick(gifImage: GifImageGridItemModel) {
        viewModelScope.launch {
            interactor.ignoreGif(gifImage.id)
        }
    }

    fun onGifItemClick(gifImage: GifImageGridItemModel, position: Int) {
        navigationDispatcher.navigate {
            it.navigate(
                GifGridFragmentDirections.actionGifGridFragmentToGifDetailsFragment(
                    position,
                    savedStateHandle[SEARCH_TERM_VALUE] ?: ""
                )
            )
        }
    }

    private companion object {
        const val SEARCH_TERM_VALUE = "SEARCH_TERM"
        const val SEARCH_TIMEOUT = 300L
    }
}
