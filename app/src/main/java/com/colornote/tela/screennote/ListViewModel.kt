package com.colornote.tela.screennote

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colornote.data.NoteRepository
import com.colornote.feature_note.util.GetNotes
import com.colornote.feature_note.util.NoteOrder
import com.colornote.feature_note.util.OrderType
import com.colornote.feature_note.util.UiEvent
import com.colornote.tela.theme.navegacao.AddEditNoteRoute
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

// Indica que a classe precisa do Android S (API 31) ou superior
@RequiresApi(Build.VERSION_CODES.S)
class ListViewModel(
    private val repository: NoteRepository, // acesso ao banco de dados
    private val getNotesUseCase: GetNotes    // caso de uso para buscar notas

) : ViewModel() {

    // Estado mutável interno da tela

    private val _state = mutableStateOf(NotesState())
    // Estado imutável exposto para a UI
    val state: State<NotesState> = _state

    private var getNoteJob: Job? = null // Job usado para controlar coroutine de busca

    // Canal para enviar eventos de UI (como navegação ou snackbars)
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow() // transforma em Flow para a UI observar

    init {
        // Ao iniciar o ViewModel, carrega notas por data decrescente
        getNotes(NoteOrder.Date(OrderType.Descending))
    }



    // Função chamada pela UI para enviar eventos da tela
    fun onEvent(event: ListNoteEvent) {
        when (event) {

            // Deleta uma nota
            is ListNoteEvent.DeleteNote -> deletes(event.id)

            // Navega para tela de adicionar/editar nota
            is ListNoteEvent.AddEditNote -> {
                viewModelScope.launch {
                    delay(100) // delay para animação ou feedback
                    _uiEvent.send(
                        UiEvent.Navigate(
                            AddEditNoteRoute(
                                event.id,         // id da nota
                                event.noteColor,  // cor da nota
                                event.timestamp   // timestamp da nota
                            )
                        )
                    )
                }
            }

            // Altera a ordenação das notas
            is ListNoteEvent.Order -> {
                // Se a ordenação atual for igual à nova, não faz nada
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) return
                getNotes(event.noteOrder) // busca notas com nova ordenação
            }

            // Alterna visibilidade da seção de ordenação
            ListNoteEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    // Função para deletar nota usando o repository
    private fun deletes(id: Long) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }

    // Função para buscar notas do banco
    private fun getNotes(noteOrder: NoteOrder) {
        getNoteJob?.cancel() // cancela qualquer busca anterior
        _state.value = state.value.copy(isLoading = true) // indica carregamento

        getNoteJob = getNotesUseCase(noteOrder)
            .onEach { notes ->
                // Atualiza o estado com as notas retornadas
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder,
                    isLoading = false // carregamento concluído
                )
            }
            .launchIn(viewModelScope) // inicia coroutine no escopo do ViewModel
    }
}
