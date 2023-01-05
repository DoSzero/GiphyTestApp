package com.dk.giphytestapp.utils

import androidx.navigation.NavController
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

typealias NavigationCommand = (NavController) -> Unit

@ActivityRetainedScoped
class NavigationDispatcher @Inject constructor() {
    private val navigationChannel = Channel<NavigationCommand>(Channel.UNLIMITED)
    val navigationCommandEvents = navigationChannel.receiveAsFlow()

    fun navigate(navigationCommand: NavigationCommand) {
        navigationChannel.trySend(navigationCommand)
    }
}
