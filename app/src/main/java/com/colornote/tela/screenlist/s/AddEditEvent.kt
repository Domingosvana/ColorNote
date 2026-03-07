package com.colornote.tela.screenlist.s

import androidx.compose.ui.focus.FocusState



    sealed class AddEditNoteEvent {
        data class EnteredTitle(val title: String): AddEditNoteEvent()
        data class TitleChanged(val focusState: FocusState): AddEditNoteEvent()
        data class EnteredContent(val content: String): AddEditNoteEvent()
        data class ContentChanged(val focusState: FocusState): AddEditNoteEvent()
        data class ChangedColor(val color: Int): AddEditNoteEvent()


    data object Save:AddEditNoteEvent()

}