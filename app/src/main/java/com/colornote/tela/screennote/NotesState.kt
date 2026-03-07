package com.colornote.tela.screennote

// Importações necessárias
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.colornote.feature_note.model.Note
import com.colornote.feature_note.util.NoteOrder
import com.colornote.feature_note.util.OrderType

// ----------------------
// CLASSE DE ESTADO
// ----------------------
data class NotesState(
    val notes: List<Note> = emptyList(), // Lista de notas que será exibida na tela
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending), // Ordem inicial: por data, decrescente
    val isOrderSectionVisible: Boolean = false, // Controla se a seção de filtros está visível
    val isLoading: Boolean = false              // Indica se está carregando dados
)

// ----------------------
// VIEWMODEL
// ----------------------
class MeuViewModel : ViewModel() {

    // Estado interno mutável -> só pode ser alterado dentro do ViewModel
    private val _state = mutableStateOf(NotesState())

    // Estado imutável exposto para a UI (a tela só pode observar, não modificar direto)
    val state: State<NotesState> = _state
}
