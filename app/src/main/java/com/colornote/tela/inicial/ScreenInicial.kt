package com.colornote.tela.inicial
// Pacote onde está a tela inicial (Splash)

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.colornote.R
import com.colornote.tela.tela.theme.ColorNoteTheme

import com.colornote.tela.theme.navegacao.ListRoute
import com.colornote.tela.theme.navegacao.SplashRoute
// import com.colornote.tela.theme.tela.screennote.screenListContent
import kotlinx.coroutines.delay
// Imports do Jetpack Compose, Navegação e Delay para temporizador da splash

// Função principal da tela inicial
@Composable
fun ScreenInicial(navController: NavHostController) {
    ScreenInicialcontent(navController) // Chama o conteúdo da tela
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenInicialcontent(navController: NavHostController) {

    // Scaffold -> estrutura de layout com suporte a elementos padrão (topbar, snackbar, etc.)
    Scaffold { innerPadding ->
        // Box -> layout que empilha elementos
        Box(
            modifier = Modifier
                .padding(innerPadding) // respeita o padding do Scaffold
                .fillMaxSize() // ocupa toda a tela
                .background(color = Color.Black), // fundo preto
            contentAlignment= Alignment.Center
        ) {
            // Imagem central da Splash
            Image(
                painter = painterResource(id = R.drawable.notlist), // recurso drawable
                contentDescription = "notlist", // descrição para acessibilidade
                modifier = Modifier
                    .size(150.dp) // tamanho fixo
                    .align(Alignment.Center) // centraliza na tela
            )
        }

        // Efeito colateral que roda apenas 1 vez quando a tela abre
        LaunchedEffect(Unit) {
            delay(2500) // espera 2.5 segundos
            navController.navigate(ListRoute) { // navega para a tela de lista
                // Remove a Splash da pilha de navegação
                popUpTo(SplashRoute) { inclusive = true }
            }
        }
    }
}

// Preview da tela (aparece no Android Studio sem precisar rodar o app)
@Composable
@Preview
fun ScreenInicialPreview() {
    ColorNoteTheme {
        val navController = rememberNavController()
        ScreenInicialcontent(navController = navController)
    }
}
