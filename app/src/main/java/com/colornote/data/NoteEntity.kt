package com.colornote.data
// Define o pacote onde esse arquivo pertence (organização do projeto)

import androidx.room.Entity
import androidx.room.PrimaryKey
// Importa as anotações do Room para criar tabelas e chaves primárias

// Marca essa classe como uma entidade do banco de dados Room
// "notes" será o nome da tabela criada no banco
@Entity(tableName = "notes")
data class NoteEntity(
    // Define a chave primária da tabela
    // autoGenerate = true -> o Room gera o ID automaticamente
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // Campo obrigatório: título da nota
    val title: String,

    // Campo opcional (pode ser nulo): conteúdo da nota
    val content: String?,

    // Campo opcional: cor da nota (representada por um Int - ex: código ARGB)
    val color: Int?,

    // Campo opcional: data/hora em milissegundos (quando foi criada/atualizada)
    val timestamp: Long?
)
