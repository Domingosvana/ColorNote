package com.colornote.tela.screennote

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.colornote.R
import com.colornote.data.NoteDatabaseProvider
import com.colornote.data.NoteRepositoryImpl
import com.colornote.feature_note.model.Note
import com.colornote.feature_note.model.note1
import com.colornote.feature_note.util.GetNotes
import com.colornote.feature_note.util.NoteOrder
import com.colornote.feature_note.util.UiEvent
import com.colornote.tela.theme.navegacao.AddEditNoteRoute

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.colornote.tela.screennote.components.ItemNote
import com.colornote.tela.screennote.components.OrderSection
import com.colornote.tela.tela.theme.ColorNoteTheme
import com.colornote.tela.tela.theme.Pink40
import com.colornote.tela.theme.tela.screennote.SearchViewModel


// ---- Tela principal de lista de notas ----
@SuppressLint("UnrememberedMutableState") // evita warnings sobre state não lembrado
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ScreenList(
    // Função de navegação para tela de adicionar/editar nota
    navigateToAddEditNoteScreen: (id: Long?, color: Int?, timestamp: Long?) -> Unit,
    notes: List<Note> // lista de notas da tela
) {
    val context = LocalContext.current.applicationContext
    val database = NoteDatabaseProvider.provide(context) // instancia DB
    val repository = NoteRepositoryImpl(dao = database.noteDao) // instancia repository

    // Cria ViewModel de lista e de busca
    val listViewModel: ListViewModel = viewModel {
        ListViewModel(repository, GetNotes(repository))
    }
    val searchViewModel: SearchViewModel = viewModel { SearchViewModel() }

    val uiState = listViewModel.state.value // estado da tela

    // Estados derivados para controlar visibilidade de conteúdo
    val showContent by derivedStateOf {
        !uiState.isLoading && uiState.notes.isNotEmpty()
    }
    val showEmptyState by derivedStateOf {
        !uiState.isLoading && uiState.notes.isEmpty() && searchViewModel.searchQuery.value.isBlank()
    }

    // Atualiza a lista de busca sempre que notas mudam
    LaunchedEffect(uiState.notes) {
        searchViewModel.updateAllNotes(uiState.notes)
    }

    // Observa query e resultados da busca
    val searchQuery by searchViewModel.searchQuery.collectAsState()
    val searchResults by searchViewModel.searchResults.collectAsState()

    // Observa eventos de UI (como navegação)
    LaunchedEffect(Unit) {
        listViewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate -> {
                    if (uiEvent.route is AddEditNoteRoute) {
                        val route = uiEvent.route
                        // Navega para tela de adicionar/editar nota
                        navigateToAddEditNoteScreen(route.id, route.noteColor, route.timestamp)
                    }
                }
                UiEvent.NavigateBack -> {} // Não usado aqui
                is UiEvent.ShowSnackbar -> {} // Não usado aqui
            }
        }
    }

    // Chamando função de conteúdo real da tela
    screenListContent(
        notes = searchResults, // notas filtradas
        onAddItemclick = { navigateToAddEditNoteScreen(null, null, null) },
        onDelete = { id -> listViewModel.onEvent(ListNoteEvent.DeleteNote(id)) },
        onOpen = { id, color, ts -> listViewModel.onEvent(ListNoteEvent.AddEditNote(id, color, ts)) },
        state = uiState,
        onToggleOrderSection = { listViewModel.onEvent(ListNoteEvent.ToggleOrderSection) },
        onOrderChange = { order -> listViewModel.onEvent(ListNoteEvent.Order(order)) },
        searchQuery = searchQuery,
        onSearchQueryChange = { searchViewModel.onSearchQueryChanged(it) },
        showContent = showContent,
        showEmptyState = showEmptyState,
        isLoading = uiState.isLoading
    )
}

// ---- Conteúdo real da tela, usado pelo Scaffold ----
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun screenListContent(
    notes: List<Note>,
    onAddItemclick: () -> Unit,
    onDelete: (Long) -> Unit,
    onOpen: (Long?, Int?, Long?) -> Unit,
    state: NotesState,
    onToggleOrderSection: () -> Unit,
    onOrderChange: (NoteOrder) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    showContent: Boolean,
    showEmptyState: Boolean,
    isLoading: Boolean
) {
    // Estado derivado para detectar lista de busca vazia
    val showSearchEmptyState by remember(searchQuery, notes, isLoading) {
        derivedStateOf {
            searchQuery.isNotBlank() && notes.isEmpty() && !isLoading
        }
    }

    // Decide se o header deve aparecer
    val shouldShowHeader by remember(showContent, showEmptyState) {
        derivedStateOf { showContent || showEmptyState }
    }

    // ---- Scaffold da tela ----
    Scaffold(
        topBar = { ColorNoteTopBar() }, // TopBar fixa
        modifier = Modifier.background(Pink40),
        floatingActionButton = {
            Box(modifier = Modifier.fillMaxSize().fillMaxWidth()) {
                FloatingActionButton(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    onClick = onAddItemclick, // FAB para adicionar nota
                    containerColor = Color(0xFF2F2E2E),
                    contentColor = Color.White,
                    shape = RoundedCornerShape(200.dp)
                ) {
                    Icon(modifier = Modifier.align(Alignment.Center),
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add note", // traduzido para inglês
                    )
                }
            }
        }
    ) { paddingValues -> // content lambda
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
                .padding(paddingValues)
                .padding(start =6.dp,end=6.dp)
        ) {
            Spacer(Modifier.height(16.dp)) // espaço superior

            // Campo de pesquisa
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = { Text("Search notes") }, // inglês
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFF2F2E2E),
                    unfocusedContainerColor = Color(0xFF2F2E2E),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(15.dp)
            )

            Spacer(Modifier.height(16.dp))

            // Header com título e botão de menu para ordenar
            AnimatedVisibility(
                visible = shouldShowHeader && !showSearchEmptyState,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth().background(color = Color.Transparent),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Your notes", color = Color.LightGray) // inglês
                        IconButton(onClick = onToggleOrderSection) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = null,
                                tint = Color.LightGray
                            )
                        }
                    }

                    // Seção de ordenação com animação
                    AnimatedVisibility(
                        visible = state.isOrderSectionVisible,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        OrderSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray)
                                .padding(vertical = 1.dp),
                            noteOrder = state.noteOrder,
                            onOrderChange = onOrderChange
                        )
                    }

                    Spacer(Modifier.height(16.dp))
                }
            }

            // Conteúdo principal da lista
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                when {
                    // Tela de carregamento
                    isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.White)
                        }
                    }
                    // Resultado de pesquisa vazio
                    showSearchEmptyState -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img),
                                contentDescription = "No search results",
                                modifier = Modifier.size(120.dp)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "No notes found for your search",
                                color = Color.LightGray,
                                modifier = Modifier.padding(horizontal = 32.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    // Tela inicial vazia
                    showEmptyState -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img),
                                contentDescription = "No notes available",
                                modifier = Modifier.size(120.dp)
                            )
                        }
                    }
                    // Lista de notas
                    showContent -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 8.dp) // espaço para FAB
                        ) {
                            itemsIndexed(
                                items = notes,
                                key = { _, note -> note.id } // chave única
                            ) { index, note ->
                                ItemNote(
                                    note = note,
                                    modifier = Modifier,
                                    cornerRadius = 0.dp,
                                    cutCornerSize = 20.dp,
                                    onDeleteClick = { onDelete(note.id) },
                                    onItemClick = { onOpen(note.id, note.color, note.timestamp) }
                                )
                                if (index < notes.lastIndex) Spacer(Modifier.height(8.dp)) // separador
                            }
                        }
                    }
                }
            }
        }
    }
}

    //@OptIn(ExperimentalMaterial3Api::class)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ColorNoteTopBar() {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "ColorNote",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.LightGray
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Pink40)
        )
    }

@Composable
@Preview
fun ScreenListContentPreview() {
    ColorNoteTheme {
        screenListContent(
            notes = listOf(note1),
            onAddItemclick = {},
            onDelete = {},
            onOpen = { _, _, _ -> },
            state = NotesState(),
            onToggleOrderSection = {},
            onOrderChange = {},
            searchQuery = "",
            onSearchQueryChange = {},
            showContent = true,
            showEmptyState = false,
            isLoading =true
        )
    }
}
