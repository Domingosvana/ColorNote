package com.colornote.data
// Pacote da camada de dados

import com.colornote.feature_note.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
// Importa o modelo de domínio (Note) e Flow + map para transformar os dados vindos do banco

// Implementa a interface NoteRepository, usando o DAO como dependência
class NoteRepositoryImpl (
    private val dao: NoteDao // DAO injetado para acessar o banco
): NoteRepository {

    // Insere ou atualiza uma nota
    override suspend fun insert(
        title: String,
        content: String?,
        id: Long?,
        color: Int,
        timestamp: Long
    ) {
        // Se o id NÃO for nulo -> está editando uma nota existente
        val entity = id?.let {
            // Busca a nota atual no banco e cria uma cópia atualizada
            dao.getBy(id)?.copy(
                title = title,
                content = content,
                color = color,
                timestamp = System.currentTimeMillis() // atualiza a data/hora
            )
        }
        // Se id for nulo -> cria uma NOVA entidade
            ?: NoteEntity(
                title = title,
                content = content,
                color = color,
                timestamp = System.currentTimeMillis()
            )

        // Salva no banco (Room decide se insere ou substitui)
        dao.insert(entity)
    }

    // Deleta nota pelo ID
    override suspend fun delete(id: Long) {
        dao.deleteById(id)
    }

    // (Versão alternativa comentada: poderia deletar carregando a nota antes e chamando delete direto no DAO)
    /*
    override suspend fun delete(id: Long) {
        val existentEntity = dao.getBy(id) ?: return
        dao.delete(existentEntity)
    }
    */

    // Retorna todas as notas como Flow<List<Note>>
    // Transforma NoteEntity (do banco) em Note (modelo usado no app)
    override fun getAll(): Flow<List<Note>> {
        return dao.getAll().map { entities ->
            entities.map { entity ->
                Note(
                    id = entity.id,
                    title = entity.title,
                    content = entity.content,
                    color = entity.color,
                    timestamp = entity.timestamp
                )
            }
        }
    }

    // Busca uma nota por ID e converte de NoteEntity para Note
    override suspend fun getBy(id: Long): Note? {
        return dao.getBy(id)?.let { entity ->
            Note(
                id = entity.id,
                title = entity.title,
                content = entity.content,
                color = entity.color,
                timestamp = entity.timestamp
            )
        }
    }

    // Busca notas por título/conteúdo e transforma de Entity -> Note
    override fun searchNotes(query: String): Flow<List<Note>> =
        dao.searchNotes(query).map { entities ->
            entities.map { entity ->
                Note(
                    id = entity.id,
                    title = entity.title,
                    content = entity.content,
                    color = entity.color,
                    timestamp = entity.timestamp
                )
            }
        }
}
