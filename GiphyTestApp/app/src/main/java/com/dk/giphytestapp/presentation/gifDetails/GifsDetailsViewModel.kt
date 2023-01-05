package com.dk.giphytestapp.presentation.gifDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.map
import com.dk.giphytestapp.domain.interactor.GifInteractor
import com.dk.giphytestapp.presentation.gifGrid.model.toItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class GifsDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: GifInteractor
) : ViewModel() {

    private val args by lazy { GifDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle) }

    val gifsFlow = interactor
        .getPaging(args.searchTerm, false)
        .map { pagingData -> pagingData.map { it.toItemModel() } }
}
