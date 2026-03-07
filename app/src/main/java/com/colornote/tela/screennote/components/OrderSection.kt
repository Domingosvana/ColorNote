package com.colornote.tela.screennote.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colornote.feature_note.util.NoteOrder
import com.colornote.feature_note.util.OrderType

// Composable que exibe a seção de ordenação das notas
@Composable
fun OrderSection(
    modifier: Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending), // ordenação inicial
    onOrderChange: (NoteOrder) -> Unit // callback para alterar a ordenação
){
    Column(
        modifier = modifier
            .background(Color(0xFF2F2E2E)) // fundo cinza escuro
    ) {

        // --- Primeira linha: critério de ordenação (Title, Date, Color) ---
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(Color(0xFF2F2E2E)) // fundo da linha
        ) {
            DefaultRadioButton(
                text = "Title", // traduzido para inglês
                selected = noteOrder is NoteOrder.Title, // seleciona se for Title
                onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) } // muda para Title mantendo ordem atual
            )
            Spacer(modifier = Modifier.width(8.dp)) // espaçamento entre botões

            DefaultRadioButton(
                text = "Date", // traduzido para inglês
                selected = noteOrder is NoteOrder.Date,
                onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) } // muda para Date
            )
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Color", // traduzido para inglês
                selected = noteOrder is NoteOrder.Color,
                onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) } // muda para Color
            )
        }

        Spacer(Modifier.height(16.dp)) // espaçamento vertical entre linhas

        // --- Segunda linha: direção da ordenação (Ascending / Descending) ---
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending", // traduzido para inglês
                selected = noteOrder.orderType is OrderType.Ascending, // selecionado se for Ascending
                onSelect = {
                    // muda a direção para Ascending mantendo o critério
                    when(noteOrder){
                        is NoteOrder.Title ->{
                            onOrderChange(NoteOrder.Title(OrderType.Ascending))
                        }
                        is NoteOrder.Date ->{
                            onOrderChange(NoteOrder.Date(OrderType.Ascending))
                        }
                        is NoteOrder.Color ->{
                            onOrderChange(NoteOrder.Color(OrderType.Ascending))
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Descending", // traduzido para inglês
                selected = noteOrder.orderType is OrderType.Descending, // selecionado se for Descending
                onSelect = {
                    // muda a direção para Descending mantendo o critério
                    when(noteOrder){
                        is NoteOrder.Title ->{
                            onOrderChange(NoteOrder.Title(OrderType.Descending))
                        }

                        is NoteOrder.Date ->{
                            onOrderChange(NoteOrder.Date(OrderType.Descending))
                        }

                        is NoteOrder.Color ->{
                            onOrderChange(NoteOrder.Color(OrderType.Descending))
                        }
                    }
                }
            )
        }
    }
}

// Preview para visualizar no Android Studio
@Composable
@Preview
fun OrderSectionpreview(){
    OrderSection(
        modifier = Modifier,
        onOrderChange = {} // preview não altera nada
    )
}
