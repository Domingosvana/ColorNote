import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.colornote.tela.theme.tela.tela.theme.ColorNoteTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit,
    maxLines: Int = Int.MAX_VALUE,
    isHintVisible: Boolean = true,
    modifier: Modifier = Modifier
){
    Box(modifier = modifier){
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            maxLines = maxLines,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {onFocusChange(it)}
        )

        if(isHintVisible){
            Text(
                text = hint, style = textStyle,
                color = Color.DarkGray
            )
        }




    }


}















@Composable
@Preview fun ScreenListContentPreview(){
    ColorNoteTheme {
        TransparentHintTextField(
            text = "text",
            hint = "",
            onValueChange = {},
            textStyle = TextStyle(),
            singleLine = false,
            onFocusChange = {},
            maxLines = Int.MAX_VALUE,
            isHintVisible = true,
            modifier = Modifier
        )
    }
}