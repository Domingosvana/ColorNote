package com.colornote.tela.screennote.components

// Importações necessárias do Compose
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DefaultRadioButton(
    text:String,                 // Texto exibido ao lado do botão
    selected:Boolean,            // Define se o botão está marcado ou não
    onSelect:()->Unit,           // Função chamada ao clicar
    modifier: Modifier = Modifier // Permite customizar o layout externamente
){
    Row(                           // Organiza os elementos em linha (horizontal)
        modifier = modifier
            .background(Color(0xFF2F2E2E)), // Fundo da linha cinza escuro
        verticalAlignment = Alignment.CenterVertically // Centraliza itens verticalmente
    ){
        RadioButton(              // Botão de seleção circular
            modifier= Modifier
                .background(Color(0xFF2F2E2E)), // Fundo igual ao da Row
            selected = selected,  // Define se o botão aparece marcado
            onClick = onSelect,   // Chama a função ao clicar
            colors = RadioButtonDefaults.colors( // Personaliza cores
                selectedColor =  Color.LightGray,             // Cor quando marcado
                unselectedColor = MaterialTheme.colorScheme.onBackground // Cor quando desmarcado
            )
        )
        Spacer(modifier = Modifier.width(8.dp)) // Espaço entre botão e texto
        Text(
            text = text,                             // Exibe o texto recebido
            style = MaterialTheme.typography.bodyLarge, // Usa estilo de texto do tema
            color = Color.LightGray                  // Cor clara para contraste
        )
    }
}


// Função de Preview para visualizar no Android Studio
@Composable
@Preview
fun DefaultRadioButtonPreview(){
    DefaultRadioButton(
        text = "Título",    // Texto do preview
        selected = true,    // Aparece já marcado
        onSelect = {}       // Não faz nada no preview
    )
}
