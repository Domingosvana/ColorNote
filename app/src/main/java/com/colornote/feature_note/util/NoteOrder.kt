package com.colornote.feature_note.util
// Pacote de utilitários (aqui ficam classes de ordenação, helpers, etc.)

// Sealed class -> permite criar subclasses específicas e "fechadas"
// Aqui, representa os critérios de ordenação: Title, Date, Color
sealed class NoteOrder(val orderType: OrderType) {

    // Ordenação por título (A-Z ou Z-A)
    class Title(orderType: OrderType): NoteOrder(orderType)

    // Ordenação por data (mais recente ou mais antiga)
    class Date(orderType: OrderType): NoteOrder(orderType)

    // Ordenação por cor (ordem crescente ou decrescente)
    class Color(orderType: OrderType): NoteOrder(orderType)

    // Função copy: retorna uma nova instância de NoteOrder mantendo o "tipo"
    // mas trocando apenas o orderType (Ascending ou Descending)
    fun copy(orderType: OrderType): NoteOrder {
        return when (this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}
