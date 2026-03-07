package com.colornote.tela.theme.tela.screennote.components

import android.app.AlertDialog
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.PopupProperties
import androidx.core.graphics.ColorUtils
import com.colornote.feature_note.model.Note
import com.colornote.feature_note.model.note1
import com.colornote.feature_note.model.note2
import com.colornote.tela.theme.tela.tela.theme.ColorNoteTheme

/**
 * Composable que desenha um item de nota (card customizado).
 * - Traduzi os textos visíveis para o usuário para INGLÊS.
 * - Adicionei comentários em português resumidos explicando cada bloco.
 */
@Composable
fun ItemNote(
    note: Note,
    onItemClick: () -> Unit,
    modifier: Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    onDeleteClick: () -> Unit,
) {
    // estado do menu (aberto/fechado)
    var expanded by remember { mutableStateOf(false) }
    // estado para controlar exibição do diálogo de confirmação de exclusão
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Surface com comportamento de clique que envolve todo o cartão
    Surface(
        onClick = onItemClick,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .animateContentSize() // anima mudanças de tamanho no conteúdo
                .background(color = Color.Black) // fundo por trás do cartão
        ) {
            // --- Desenho do cartão (parte de background recortada) ---
            Canvas(modifier = Modifier.matchParentSize()) {
                // cria um path com um canto "cortado" no topo direito
                val clipPath = Path().apply {
                    lineTo(size.width - cutCornerSize.toPx(), 0f)
                    lineTo(size.width, cutCornerSize.toPx())
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }

                // aplica clip e desenha um rect arredondado com a cor da nota
                clipPath(clipPath) {
                    drawRoundRect(
                        color = Color(note.color ?: 0),
                        size = size,
                        cornerRadius = CornerRadius(cornerRadius.toPx())
                    )
                }
            }

            // --- Conteúdo do cartão: título e conteúdo ---
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp) // padding interno do cartão
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = (Color.Black),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis // mostra "..." se ultrapassar
                )

                Spacer(Modifier.height(6.dp))

                note.content?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = (Color.Black),
                        maxLines = 10,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // --- Botão de menu no canto superior direito ---
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            ) {
                // IconButton que abre o dropdown menu
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu", // acessibilidade
                        tint = Color.Black
                    )
                }

                // Dropdown com opções (agora os textos estão em inglês)
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(Color(0xFF2F2E2E)),
                    offset = DpOffset(200.dp, 0.dp)
                ) {
                    // Opção "Rename"
                    DropdownMenuItem(
                        text = { Text("Rename", color = Color.Green) },
                        onClick = {
                            expanded = false
                            onItemClick() // reusa onItemClick para navegar/editar
                        }
                    )
                    // Opção "Delete"
                    DropdownMenuItem(
                        text = { Text("Delete", color = Color.Red) },
                        onClick = {
                            expanded = false
                            showDeleteDialog = true // abre diálogo de confirmação
                        }
                    )
                }
            }
        }
    }

    // --- Diálogo de confirmação de exclusão (em inglês) ---
    if (showDeleteDialog) {
        androidx.compose.material3.AlertDialog(
            modifier = Modifier.background(Color(0xFF2F2E2E)), // cor do container
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    "Delete note", // Título em inglês
                    color = Color.White
                )
            },
            text = {
                Text(
                    "Are you sure you want to delete this note?", // Texto em inglês
                    color = Color.LightGray
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick() // chama callback de exclusão
                        showDeleteDialog = false
                    }
                ) {
                    Text(
                        "Yes", // confirma em inglês
                        color = Color.Green
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text(
                        "No", // cancela em inglês
                        color = Color.Red
                    )
                }
            },
            containerColor = Color(0xFF2F2E2E),
            tonalElevation = 8.dp
        )
    }
}

@Composable
@Preview
fun ItemNotePreview() {
    ColorNoteTheme {
        ItemNote(
            note = note1,
            modifier = Modifier,
            cornerRadius = 0.dp,
            cutCornerSize = 20.dp,
            onDeleteClick = {},
            onItemClick = {}
        )
    }
}

@Composable
@Preview
fun ItemNotePreview1() {
    ColorNoteTheme {
        ItemNote(
            note = note2,
            modifier = Modifier,
            cornerRadius = 10.dp,
            cutCornerSize = 30.dp,
            onDeleteClick = {},
            onItemClick = {}
        )
    }
}
