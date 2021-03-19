package com.yuxiu.mydiary.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


public data class MyPageEntity(
    var texts:ArrayList<MyPageTextEntity> = arrayListOf(),
    var images: ArrayList<MyPageImageEntity> = ArrayList()
)
public data class MyPageImageEntity(
       var offsetXFrame: MutableState<Float> = mutableStateOf(0f),
        var offsetYFrame: MutableState<Float> = mutableStateOf(0f),
        var offsetX: MutableState<Float> = mutableStateOf(120f),
        var offsetY: MutableState<Float> = mutableStateOf(90f),
        var imageW: MutableState<Float> = mutableStateOf(40f) ,
        var imageH: MutableState<Float> = mutableStateOf(30f),
        var imagePath:String ="",
        var bl: MutableState<Float> = mutableStateOf(1f),
    )
public data class MyPageTextEntity(
    var offsetXFrame: MutableState<Float> = mutableStateOf(0f),
    var offsetYFrame: MutableState<Float> = mutableStateOf(0f),
    var offsetX: MutableState<Float> = mutableStateOf(80f),
    var offsetY: MutableState<Float> = mutableStateOf(25f),
    var textContent:String="",
    var textColor:Color= Color.Black,
    var bgColor:Color=Color.Transparent
)
@Composable
fun MyPage(images: ArrayList<MyPageImageEntity?>,texts: ArrayList<MyPageTextEntity?>): Unit {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)

    ) {
        for (image in images ){
            image?.let {
                getImage(image)
            }
        }
        for (text in texts ){
            text?.let {
                getText(text)
            }
        }
    }
}






