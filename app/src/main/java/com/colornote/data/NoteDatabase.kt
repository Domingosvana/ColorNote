package com.colornote.data
// Pacote onde está o banco de dados

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
// Imports para configurar o banco com Room

// Define a classe como um banco de dados Room
// entities -> quais tabelas ele contém (aqui só NoteEntity)
// version -> versão do banco (se mudar estrutura, precisa aumentar)
@Database(
    entities = [NoteEntity::class],
    version = 8
)
abstract class NoteDatabase: RoomDatabase() {
    // Expondo o DAO para acessar o banco
    abstract val noteDao: NoteDao
}

// Singleton que fornece uma instância única do banco
object NoteDatabaseProvider {

    // @Volatile -> garante visibilidade entre threads (sempre pega o valor atualizado da variável)
    @Volatile
    private var INSTANCE: NoteDatabase? = null

    // Função para criar/retornar o banco de dados
    fun provide(context: Context): NoteDatabase {

        // Se já existe, retorna. Caso contrário cria de forma sincronizada
        return INSTANCE ?: synchronized(this) {
            // Cria a instância do banco
            val instance = Room.databaseBuilder(
                context.applicationContext,   // evita memory leaks
                NoteDatabase::class.java,     // classe do banco
                "note_database"               // nome do arquivo do banco
            )
                // fallbackToDestructiveMigration(false):
                // Se mudar versão sem fornecer migração, o Room tentaria destruir o banco.
                // Aqui está configurado para NÃO destruir (false).
                .fallbackToDestructiveMigration(false)
                .build()

            // Salva a instância para uso futuro
            INSTANCE = instance
            instance
        }
    }
}
