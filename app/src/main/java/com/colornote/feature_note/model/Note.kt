package com.colornote.feature_note.model


import androidx.compose.ui.graphics.Color
import com.colornote.tela.theme.tela.tela.theme.Amarelo
import com.colornote.tela.theme.tela.tela.theme.BabyBlue
import com.colornote.tela.theme.tela.tela.theme.Blue
import com.colornote.tela.theme.tela.tela.theme.Branco
import com.colornote.tela.theme.tela.tela.theme.Cinzaclaro
import com.colornote.tela.theme.tela.tela.theme.Cinzaescuro
import com.colornote.tela.theme.tela.tela.theme.Fundo
import com.colornote.tela.theme.tela.tela.theme.Green
import com.colornote.tela.theme.tela.tela.theme.Laranja
import com.colornote.tela.theme.tela.tela.theme.LightGreen
import com.colornote.tela.theme.tela.tela.theme.Orange
import com.colornote.tela.theme.tela.tela.theme.Red
import com.colornote.tela.theme.tela.tela.theme.RedOrange
import com.colornote.tela.theme.tela.tela.theme.RedPink
import com.colornote.tela.theme.tela.tela.theme.Violet

data class Note(
    val id: Long,
    val title: String,
    val content: String?,
    val color: Int?,
    val timestamp: Long?
){

    companion object {
     val noteColorInts = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink, Laranja , Amarelo , Branco ,
         Red,Blue, Green,Orange
     )
 }

    val Red = Color(0xFFD53A2F)
    val Blue = Color(0xFF2965C9)
    val Green = Color(0xFF1E9651)
    val Orange = Color(0xFFFF9800)
















}


val note1 = Note(
    id = 1,
    title = "Título da Nota 1",
    content = "Conteúdo da Nota 1 Como Expressão (Expression): A última expressão em um bloco if ou else é o valor retornadopela expressão como um todo. Isso permite atribuições diretas. onteúdo da Nota 1 Como Expressão (Expression): A última expressão em um bloco if ou else é o valor retornado pela expressão  como um todo. Isso permite atribuições diretas.",
    color = BabyBlue.hashCode(),
    timestamp = System.currentTimeMillis()
)

val note2 =Note(
    id = 2,
    title = "Título da Nota 2",
    content = "Conteúdo da Nota 1 Como Expressão (Expression): A última expressão em um bloco if ou else é o valor retornadopela expressão como um todo. Isso permite atribuições diretas. onteúdo da Nota 1 Como Expressão (Expression): A última expressão em um bloco if ou else é o valor retornado pela expressão  como um todo. Isso permite atribuições diretas.",
    color = LightGreen.hashCode(),
    timestamp = System.currentTimeMillis()
)

val note3 = Note(
    id = 3,
    title = "Título da Nota 3",
    content = "Conteúdo da Nota 3",
    color = RedOrange.hashCode(),
    timestamp = System.currentTimeMillis()
)