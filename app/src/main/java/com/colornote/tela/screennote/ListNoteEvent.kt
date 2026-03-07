package com.colornote.tela.theme.tela.screennote

import com.colornote.feature_note.util.NoteOrder

// Interface selada que representa todos os tipos de eventos da tela de lista de notas
sealed interface ListNoteEvent {

    // Evento para alterar a ordenação das notas
    data class Order(val noteOrder: NoteOrder): ListNoteEvent

    // Evento para adicionar ou editar uma nota
    // id -> null se for nova nota
    // noteColor -> cor da nota (opcional)
    // timestamp -> horário da nota (opcional)
    data class AddEditNote(
        val id: Long?,
        val noteColor: Int?,
        val timestamp: Long?
    ): ListNoteEvent

    // Evento para deletar uma nota pelo id
    data class DeleteNote(val id: Long): ListNoteEvent

    // Evento para alternar visibilidade da seção de ordenação
    object ToggleOrderSection: ListNoteEvent
}
