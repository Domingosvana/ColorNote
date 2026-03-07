package com.colornote.feature_note.util

import com.colornote.tela.theme.navegacao.AddEditNoteRoute
import com.colornote.tela.theme.navegacao.NavigationRoute

/*sealed interface UiEvent {

    data class ShowSnackbar(val message: String):UiEvent

    data object  NavigateBack:UiEvent

    data class Navigate<T:Any>(val route:T,):UiEvent
}

 */

/*sealed interface UiEvent {
    data class ShowSnackbar(val message: String): UiEvent
    data object NavigateBack: UiEvent
    data class Navigate(val route: AddEditNoteRoute): UiEvent
}

interface NavigationRoute

data class AddEditNoteRoute(val id: Long?) : NavigationRoute

 */
sealed interface UiEvent {

    data class ShowSnackbar(val message: String) : UiEvent

    data object NavigateBack : UiEvent

    data class Navigate(val route: NavigationRoute) : UiEvent
}
