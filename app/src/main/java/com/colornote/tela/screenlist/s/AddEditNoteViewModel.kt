package com.colornote.tela.screenlist.s

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colornote.data.NoteRepository
import com.colornote.feature_note.model.Note
import com.colornote.feature_note.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class AddEditNoteViewModel(
    //verificar se  a nossa tarefa e nulo ou nao para ser editado novamente
    private val id:Long?=null,
    private val repository: NoteRepository
): ViewModel(){

    var titleState by mutableStateOf(NoteTextFieldState(hint="Enter title..."))

    private set

    var contentState by mutableStateOf(NoteTextFieldState(hint="Enter content..."))

    private set

    var noteColors by mutableStateOf<Int?>(Note.noteColorInts.random().hashCode())

    //3  variaves para  notificacoes de envents
    // eventos de navegacao geral

    private val _uiEvent = Channel<UiEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()

    //verificar se  a nossa tarefa e nulo ou nao para ser editado novamente

    init{

        id?.let {

            viewModelScope.launch {
                val note = repository.getBy(id=id)
                titleState = titleState.copy(
                    text = note?.title?:"",
                    isHintVisible = false
                )
                contentState = contentState.copy(
                    text = note?.content?:"",
                    isHintVisible = false //true

                )
                noteColors = note?.color




            }

        }
    }



// Função que trata eventos da tela (como digitação ou clique em salvar)


    fun onEvent(event: AddEditNoteEvent) {

        when (event) {

            is AddEditNoteEvent.EnteredTitle -> {
                titleState = titleState.copy(text = event.title)
            }

            is AddEditNoteEvent.TitleChanged -> {
                titleState =
                    titleState.copy(isHintVisible = !event.focusState.isFocused && titleState.text.isBlank())

            }

            is AddEditNoteEvent.EnteredContent -> {
                contentState= contentState.copy(text = event.content)
            }

            is AddEditNoteEvent.ContentChanged -> {
                contentState =
                    contentState.copy(isHintVisible = !event.focusState.isFocused && contentState.text.isBlank())
            }

            is AddEditNoteEvent.ChangedColor -> {
                noteColors = event.color

            }

            is AddEditNoteEvent.Save -> {
                saveNote()
            }

        }


    }

    private fun saveNote() {
        viewModelScope.launch {
            if (titleState.text.isBlank()){
                _uiEvent.send(UiEvent.ShowSnackbar("Title can't be empty"))
                return@launch

            }

            else if (titleState.text.length < 3 ){
                _uiEvent.send(UiEvent.ShowSnackbar("Title is too short."))
                return@launch
            }

            repository.insert(
                title = titleState.text,
                content = contentState.text,
                color = noteColors?:0,
                timestamp = System.currentTimeMillis(),
                id = id
            )
            _uiEvent.send(UiEvent.NavigateBack)

        }
    }



}


















