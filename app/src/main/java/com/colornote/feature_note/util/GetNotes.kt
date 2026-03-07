package com.colornote.feature_note.util
// Pacote onde ficam as classes utilitárias e casos de uso

import com.colornote.data.NoteRepository
import com.colornote.feature_note.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
// Importa repositório, modelo Note e Flow para trabalhar com dados reativos

// Caso de uso: responsável por "pegar notas" já aplicando a ordenação escolhida
class GetNotes(private val repository: NoteRepository) {

    // Sobrecarga do operador "invoke", ou seja:
    // em vez de chamar getNotes.invoke(), pode usar apenas getNotes()
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
        // Por padrão, as notas vêm ordenadas por data, do mais recente para o mais antigo
    ): Flow<List<Note>> {
        // Pega todas as notas do repositório
        return repository.getAll().map { notes ->
            // Verifica se a ordem é Ascendente ou Descendente
            when (noteOrder.orderType) {
                // ASC -> ordena do menor para o maior
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() } // ordena A-Z
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp } // mais antigas primeiro
                        is NoteOrder.Color -> notes.sortedBy { it.color } // cores em ordem crescente
                    }
                }
                // DESC -> ordena do maior para o menor
                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() } // ordena Z-A
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp } // mais recentes primeiro
                        is NoteOrder.Color -> notes.sortedByDescending { it.color } // cores em ordem decrescente
                    }
                }
            }
        }
    }
}
