package com.colornote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Animatable // Importar Animatable
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember // Importar remember
import androidx.compose.ui.graphics.Color // Importar Color
import com.colornote.tela.theme.navegacao.NoteNavHost
import com.colornote.tela.tela.theme.ColorNoteTheme
// import com.colornote.tela.theme.navegacao.NoteNavHost // Comente se estiver testando ContentNoteScreen
import com.colornote.tela.screenlist.s.ContentNoteScreen
import com.colornote.tela.screenlist.s.NoteTextFieldState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ColorNoteTheme {
                // Valores de exemplo para ContentNoteScreen
                val exampleNoteColor = null // Ou Color.Yellow.toArgb() por exemplo
                val exampleNoteBackgroundAnimatable = remember { Animatable(Color.LightGray) } // Exemplo

                ContentNoteScreen(
                    title = NoteTextFieldState(text = "Título de Teste Main", hint = "Digite um título"),
                    content = NoteTextFieldState(
                        text = "Conteúdo de Teste Main",
                        hint = "Digite o conteúdo"
                    ),
                    onEvent = { /* Lógica de evento de exemplo ou vazia */ },
                    snackbarHostState = remember { SnackbarHostState() }, // Lembre-se do SnackbarHostState
                    noteColor = exampleNoteColor,
                    noteBackgroundAnimatable = exampleNoteBackgroundAnimatable
                )
                // Se você não está usando navegação neste momento, comente a linha abaixo:
                NoteNavHost()
            }
        }
    }
}
