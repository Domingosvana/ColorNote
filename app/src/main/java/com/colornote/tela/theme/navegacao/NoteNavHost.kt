package com.colornote.tela.theme.navegacao

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.colornote.feature_note.model.note1
import com.colornote.tela.inicial.ScreenInicial
import com.colornote.tela.screenlist.s.AddEditNoteScreen
import com.colornote.tela.screennote.ScreenList

import kotlinx.serialization.Serializable

//import com.colornote.tela.theme.tela.screennote.ScreenListContent


//private val AddEditNoteRoute.id: Long?

@Serializable
object SplashRoute  // 👈 aqui



@Serializable
object ListRoute


@Serializable
data class AddEditNoteRoute(
    val id:Long?=null,
    val noteColor:Int?=null,
    val timestamp:Long?
) : NavigationRoute





@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun  NoteNavHost(){
    val navController= rememberNavController()
    NavHost(navController=navController, startDestination =  SplashRoute) {
        composable< SplashRoute> {
            ScreenInicial(navController)
        }

    composable<ListRoute> {
        ScreenList(
            notes = listOf(note1),
            navigateToAddEditNoteScreen = { id, color,timestamp->
                navController.navigate(AddEditNoteRoute(id,color,timestamp))
            }
        )



    }

       composable<AddEditNoteRoute> {backStackEntry ->
           val addEditNoteRoute = backStackEntry.toRoute<AddEditNoteRoute>()
           AddEditNoteScreen(
               //  id = AddEditNoteRoute,
               navigateBack = { navController.popBackStack() },
               id =  addEditNoteRoute.id,
               noteColor = addEditNoteRoute.noteColor ?: -1,
               timestamp = addEditNoteRoute.timestamp?: -1
           )
       }


    }
}