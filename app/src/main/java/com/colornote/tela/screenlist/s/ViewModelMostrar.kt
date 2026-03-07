package com.colornote.tela.screenlist.s

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ViewModelMostrar: ViewModel() {
    var mostrar by mutableStateOf<Boolean>(false)

    fun onMostrar(){
        mostrar = true
    }


}