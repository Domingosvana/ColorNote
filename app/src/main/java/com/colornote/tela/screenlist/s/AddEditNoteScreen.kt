package com.colornote.tela.screenlist.s

import TransparentHintTextField
import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.colornote.data.NoteDatabaseProvider
import com.colornote.data.NoteRepositoryImpl
import com.colornote.feature_note.util.UiEvent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import com.colornote.feature_note.model.Note.Companion.noteColorInts
import com.colornote.tela.screennote.ColorNoteTopBar
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    id: Long?,
    navigateBack: () -> Unit,
    noteColor: Int, // passado pela rota (ou -1)
    timestamp: Long?
) {
    val context = LocalContext.current.applicationContext
    val database = NoteDatabaseProvider.provide(context)
    val repository = NoteRepositoryImpl(dao = database.noteDao)

    val viewModel = viewModel<AddEditNoteViewModel> {
        AddEditNoteViewModel(repository = repository, id = id)
    }

    val title = viewModel.titleState
    val content = viewModel.contentState
    val selectedNoteColor = viewModel.noteColors // Int? mantido no VM

    // cor inicial do fundo:
    val initialColorInt =
        when {noteColor != -1                 -> noteColor
        selectedNoteColor != null       -> selectedNoteColor
        else                            -> Color.White.toArgb()
    }

    val noteBackgroundAnimatable = remember {
        Animatable(Color(initialColorInt))
    }

    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(uiEvent.message)
                UiEvent.NavigateBack    -> navigateBack()
                is UiEvent.Navigate     -> { /* sem ação aqui */ }
            }
        }
    }

    ContentNoteScreen(
        title = title,
        content = content,
        onEvent = viewModel::onEvent,
        snackbarHostState = snackbarHostState,
        noteBackgroundAnimatable = noteBackgroundAnimatable,
        noteColor = selectedNoteColor // usado para desenhar o “anel” selecionado na paleta
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun ContentNoteScreen(
    title: NoteTextFieldState,
    content: NoteTextFieldState,
    noteColor: Int?, // cor atual da nota (Int? em ARGB)
    onEvent: (AddEditNoteEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
    noteBackgroundAnimatable: Animatable<Color, AnimationVector4D>,
    viewmodel:ViewModelMostrar=viewModel()
) {
    var mostrar by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Cor atual do botão baseada na cor da nota
    val buttonColor by remember(noteColor) {
        derivedStateOf {
            if (noteColor != null) Color(noteColor) else Color.White
        }
    }

    val mostra=viewmodel.mostrar

    Scaffold(
        modifier = Modifier
             ,

        topBar = { ColorNoteTopBar() },
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                    //.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                FloatingActionButton(
                    modifier = Modifier.padding(start = 35.dp),

                    onClick = { mostrar = true },
                    containerColor = buttonColor,
                    contentColor = Color.Green,
                    shape = RoundedCornerShape(CornerSize(200.dp))
                ) {
                    Icon(imageVector =if(mostrar)  Icons.Default.Check else Icons.Default.Create,
                        contentDescription = if (mostrar) "Fechar paleta" else "Abrir paleta")
                }
               // Spacer(Modifier.width(23.dp))
                FloatingActionButton(
                    onClick = {
                        mostrar
                        onEvent(AddEditNoteEvent.Save)
                    },
                    containerColor = Color(0xFF2F2E2E),
                    contentColor = Color.Green,
                    shape = RoundedCornerShape(CornerSize(200.dp))
                ) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = "save note")
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .clickable{mostrar = false}
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, top = 130.dp)
            ) {
                TransparentHintTextField(
                    text = title.text,
                    hint = title.hint,
                    onValueChange = { onEvent(AddEditNoteEvent.EnteredTitle(it)) },
                    onFocusChange = { onEvent(AddEditNoteEvent.TitleChanged(it)) },
                    isHintVisible = title.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(12.dp))

                TransparentHintTextField(
                    text = content.text,
                    hint = content.hint,
                    onValueChange = { onEvent(AddEditNoteEvent.EnteredContent(it)) },
                    onFocusChange = { onEvent(AddEditNoteEvent.ContentChanged(it)) },
                    isHintVisible = content.isHintVisible,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(15.dp)
                )
            }

            if (mostrar) {
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 180.dp) // Ajuste a distância do bottom conforme necessário
                        .fillMaxWidth(0.9f), // 90% da largura para não tocar nas bordas
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        noteColorInts.chunkedRows(6).forEach { rowColors ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                rowColors.forEach { color ->
                                    val colorInt = color.toArgb()
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp) // Tamanho um pouco menor
                                            .shadow(8.dp, CircleShape)
                                            .clip(CircleShape)
                                            .background(color)
                                            .border(
                                                width = 2.dp,
                                                color = if (noteColor == colorInt) Color.Black else Color.Transparent,
                                                shape = CircleShape
                                            )
                                            .clickable {
                                                scope.launch {
                                                    noteBackgroundAnimatable.animateTo(
                                                        targetValue = Color(colorInt),
                                                        animationSpec = tween(durationMillis = 500)
                                                    )
                                                }
                                                mostrar = false
                                                onEvent(AddEditNoteEvent.ChangedColor(colorInt))
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
private fun List<Color>.chunkedRows(chunkSize: Int): List<List<Color>> = chunked(chunkSize)

@Composable
@Preview
fun ContentNoteScreenPreview() {
    val animatable = remember { Animatable(Color.White) }
    ContentNoteScreen(
        title = NoteTextFieldState(text = "", hint = ""),
        content = NoteTextFieldState(text = "Conteúdo de teste", hint = ""),
        onEvent = {},
        snackbarHostState = SnackbarHostState(),
        noteBackgroundAnimatable = animatable,
        noteColor = null
    )
}
