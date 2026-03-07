package com.colornote.feature_note.util
// Pacote utilitário para classes auxiliares

// Sealed class -> garante que só existam subclasses definidas aqui dentro
// Representa o tipo de ordenação: Ascendente ou Descendente
sealed class OrderType {

    // Ascendente -> do menor para o maior
    // Ex: A → Z, mais antigo → mais recente, cor clara → cor escura
    object Ascending : OrderType()

    // Descendente -> do maior para o menor
    // Ex: Z → A, mais recente → mais antigo, cor escura → cor clara
    object Descending : OrderType()
}
