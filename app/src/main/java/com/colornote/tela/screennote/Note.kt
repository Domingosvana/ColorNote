//package com.colornote.tela.theme.tela.screennote
//
//// Importe suas cores e Color do Compose
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.toArgb
//import com.colornote.tela.theme.tela.tela.theme.Amarelo
//import com.colornote.tela.theme.tela.tela.theme.BabyBlue
//import com.colornote.tela.theme.tela.tela.theme.Branco
//import com.colornote.tela.theme.tela.tela.theme.Cinzaclaro
//import com.colornote.tela.theme.tela.tela.theme.Cinzaescuro
//import com.colornote.tela.theme.tela.tela.theme.Fundo
//import com.colornote.tela.theme.tela.tela.theme.Laranja
//import com.colornote.tela.theme.tela.tela.theme.LightGreen
//import com.colornote.tela.theme.tela.tela.theme.RedOrange
//import com.colornote.tela.theme.tela.tela.theme.RedPink
//import com.colornote.tela.theme.tela.tela.theme.Violet
//
//data class Note(
//    val id: Long? = null,
//    val title: String,
//    val content: String?,
//    val color: Int, // Armazena a cor como Int ARGB
//    val timestamp: Long
//) {
//    companion object {
//        // Esta lista contém os objetos Color do Compose
//        val selectableColors: List<Color> = listOf(
//            RedOrange, LightGreen, Violet, BabyBlue, RedPink, Cinzaescuro,
//            Laranja, Amarelo, Branco, Cinzaclaro, Fundo
//        )
//
//        // Se você precisa de uma lista de Ints ARGB diretamente (por exemplo, para um valor padrão)
//        val noteColorInts: List<Int> = selectableColors.map { it.toArgb() }
//    }
//}
//
//// Exemplo de como usar .toArgb() para os valores de exemplo
//val note1 = Note(
//    id = 1,
//    title = "Título da Nota 1",
//    content = "Conteúdo da Nota 1...",
//    color = BabyBlue.toArgb(), // Use .toArgb()
//    timestamp = System.currentTimeMillis()
//)
//
//val note2 = Note(
//    id = 2,
//    title = "Título da Nota 2",
//    content = "Conteúdo da Nota 2...",
//    color = LightGreen.toArgb(), // Use .toArgb()
//    timestamp = System.currentTimeMillis()
//)
//
//val note3 = Note(
//    id = 3,
//    title = "Título da Nota 3",
//    content = "Conteúdo da Nota 3",
//    color = RedOrange.toArgb(), // Use .toArgb()
//    timestamp = System.currentTimeMillis()
//)