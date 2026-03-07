package com.colornote.data
// Pacote onde a interface está organizada

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
// Imports necessários para usar Room (DAO, Querys, Insert, etc.) e Flow para dados reativos

// Marca a interface como um DAO (Data Access Object) -> responsável pelas operações no banco
@Dao
interface NoteDao {

    // Insere uma nota no banco
    // Se já existir uma nota com o mesmo id, substitui (REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: NoteEntity)

    // Deleta uma nota pelo ID
    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteById(id: Long)

    // Busca todas as notas e retorna em ordem decrescente de timestamp (mais recente primeiro)
    // Flow -> dados são observáveis, atualiza automaticamente quem estiver escutando
    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    fun getAll(): Flow<List<NoteEntity>>

    // Busca uma nota específica pelo ID
    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getBy(id: Long): NoteEntity?

    // Busca notas pelo título OU conteúdo, que contenham o texto da pesquisa (query)
    // '%' || :query || '%' -> monta o LIKE para busca parcial
    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun searchNotes(query: String): Flow<List<NoteEntity>>

}
