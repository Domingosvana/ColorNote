package com.colornote.data
// Pacote onde está a interface do repositório

import com.colornote.feature_note.model.Note
import kotlinx.coroutines.flow.Flow
// Importa o modelo da nota usado na camada de domínio (Note) e Flow para dados reativos

// Interface que define as operações que o repositório de notas deve implementar
// (inserir, deletar, buscar todas, buscar por id, pesquisar)
interface NoteRepository {

    // Insere ou atualiza uma nota
    // title -> título obrigatório
    // content -> conteúdo opcional
    // id -> opcional (se for null, cria nova nota; se tiver valor, atualiza existente)
    // color -> cor obrigatória da nota
    // timestamp -> data/hora obrigatória
    suspend fun insert(
        title: String,
        content: String?,
        id: Long? = null,
        color: Int,
        timestamp: Long
    )

    // Deleta uma nota pelo ID
    suspend fun delete(id: Long)

    // Retorna todas as notas em formato Flow (atualiza automaticamente quando mudar no banco)
    fun getAll(): Flow<List<Note>>

    // Busca uma nota pelo ID (pode retornar null se não existir)
    suspend fun getBy(id: Long): Note?

    // Busca notas por título ou conteúdo que contenham o texto pesquisado
    fun searchNotes(query: String): Flow<List<Note>>
}
