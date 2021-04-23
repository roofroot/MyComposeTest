package com.yuxiu.mydiary.ui.widget
import android.media.AudioManager
import android.view.Surface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import getMedia
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import tv.danmaku.ijk.media.player.TextureMediaPlayer
data class MyPageEntity(
    var elements:ArrayList<EditPageElement> = arrayListOf())
open class EditPageElement(  var offsetXFrame: MutableState<Float> = mutableStateOf(0f),
                             var offsetYFrame: MutableState<Float> = mutableStateOf(0f),
                             var offsetX: MutableState<Float> = mutableStateOf(120f),
                             var offsetY: MutableState<Float> = mutableStateOf(90f),
                             var index:Int=-1,
                             var isRotate: MutableState<Boolean> = mutableStateOf(false),
                             )

data class MyPageImageEntity(  var imageW: MutableState<Float> = mutableStateOf(40f) ,
                                var imageH: MutableState<Float> = mutableStateOf(30f),
                                var imagePath:String ="",
                                var imageMaxW:Float=0f,
                                var imageRotate: MutableState<Float> = mutableStateOf(0f),
                                var bl: Float = 1f,):EditPageElement()


data class MyPageMediaEntity(  var mediaW: MutableState<Float> = mutableStateOf(400f) ,
                               var mediaH: MutableState<Float> = mutableStateOf(300f),
                               var mediaPath:String ="",
                               var imageRotate: MutableState<Float> = mutableStateOf(0f),
                               var player:IjkMediaPlayer?,
                               var bl: Float = 1f,):EditPageElement()


 data class MyPageTextEntity(
    var textContent:String="",
    var textColor:Color= Color.Black,
    var bgColor:Color=Color.Transparent
):EditPageElement()

var mSurface: Surface?=null
var mPlayer: IjkMediaPlayer?=null;
var textureMediaPlayer:TextureMediaPlayer?=null;
@Composable
fun MyPage(elements: ArrayList<EditPageElement?>): Unit {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)

    ) {
        for (element in elements ){
            element?.let {
                if(element is MyPageImageEntity) {
                    getImage(element)
                }
                if(element is MyPageTextEntity){
                    getText(element)
                }
                if(element is MyPageMediaEntity){
                    getMedia(element)
                }
            }
        }
    }
}







