package com.yuxiu.mydiary.ui.widget

import android.annotation.SuppressLint
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.yuxiu.mydiary.MainActivity
import com.yuxiu.mydiary.data.editPageData
import com.yuxiu.mydiary.ui.Navigation
import kotlin.math.roundToInt

@SuppressLint("RememberReturnType")
@Composable

fun ActionWeight(entity:MyPageImageEntity) {

    val left: Float = 0f;
    val right: Float = 30f;
    val top: Float = 0f;
    val bottom: Float = 30f;
    var isActionBarVisibility= remember {
        mutableStateOf(false)
    }
    var actionBtnBg= remember {
        mutableStateOf(Color(0x0fff3e96))
    }
    if(isActionBarVisibility.value) {
        Column(Modifier
            .offset {
                IntOffset(
                    entity.offsetX.value.roundToInt(),
                    entity.offsetY.value.roundToInt() - (MainActivity.density * 100).toInt()
                )
            }
            .width(50.dp)
            .wrapContentHeight()) {
            Text("上一层", modifier = Modifier
                .size(50.dp)
                .clickable {
                    var index = editPageData.indexOf(entity)
                    var temp: EditPageElement?
                    if (index < editPageData.size - 1) {
                        temp = editPageData[index + 1]
                        editPageData[index + 1] = entity
                        editPageData[index] = temp
                        navigation.refreshState(Navigation.MYPAGESTATE)
                    }
                    isActionBarVisibility.value = false

                })
            Text("下一层", modifier = Modifier
                .size(50.dp)
                .clickable {
                    var index = editPageData.indexOf(entity)
                    var temp: EditPageElement?
                    if (index > 0) {
                        temp = editPageData[index - 1]
                        editPageData[index - 1] = entity
                        editPageData[index] = temp
                        navigation.refreshState(Navigation.MYPAGESTATE)
                    }
                    isActionBarVisibility.value = false

                })

        }
    }
    Canvas(
        Modifier
            .offset {
                IntOffset(
                    entity.offsetX.value.roundToInt(),
                    entity.offsetY.value.roundToInt()
                )
            }
            .background(actionBtnBg.value)
            .size(Dp(50f))
            .pointerInput(Unit) {
//                detectDragGestures { change, dragAmount ->
//
//                }
                detectDragGestures({
                          actionBtnBg.value= Color(0xffff3e96)
                }, {
                   actionBtnBg.value= Color(0x0fff3e96)
                }, {
                    actionBtnBg.value= Color(0x0fff3e96)
                }, { change, dragAmount ->

                    change.consumeAllChanges()
                    if (dragAmount.x > 0 && entity.offsetX.value - entity.offsetXFrame.value < MainActivity.screenW
                        && entity.offsetY.value - entity.offsetYFrame.value < MainActivity.screenH - 80 * MainActivity.density
                    ) {
                        entity.imageW.value += dragAmount.x / MainActivity.density
                        entity.offsetX.value += dragAmount.x
                        entity.imageH.value = entity.imageW.value * entity.bl
                        entity.offsetY.value =
                            entity.offsetYFrame.value + entity.imageH.value * MainActivity.density
                    }

                    if (dragAmount.x < 0 && entity.imageW.value + dragAmount.x / MainActivity.density > 40) {
                        entity.imageW.value += dragAmount.x / MainActivity.density
                        entity.offsetX.value += dragAmount.x
                        entity.imageH.value = entity.imageW.value * entity.bl
                        entity.offsetY.value =
                            entity.offsetYFrame.value + entity.imageH.value * MainActivity.density
                    }
                })
            }
            .clickable {
                isActionBarVisibility.value = !isActionBarVisibility.value
            }
    )
    {

        drawIntoCanvas { canvas ->
            val paint = Paint()
            paint.color = Color.Black
            paint.strokeWidth = 3f
            paint.style = PaintingStyle.Stroke
            var rect = Rect(
                left, top, right, bottom
            )
            canvas.drawRect(rect, paint)
//                canvas.drawCircle(Offset(50f,50f),50f,paint)
        }
    }

}
@Composable
fun ActionWeight(entity:MyPageTextEntity) {

    val left: Float = 0f;
    val right: Float = 30f;
    val top: Float = 0f;
    val bottom: Float = 30f;
    var isActionBarVisibility= remember {
        mutableStateOf(false)
    }
    var actionBtnBg= remember {
        mutableStateOf(Color(0x0f02f78e))
    }
    if(isActionBarVisibility.value) {
        Column(Modifier
            .offset {
                IntOffset(
                    entity.offsetX.value.roundToInt(),
                    entity.offsetY.value.roundToInt() - (MainActivity.density * 100).toInt()
                )
            }
            .width(50.dp)
            .wrapContentHeight()) {
            Text("上一层", modifier = Modifier
                .size(50.dp)
                .clickable {
                    var index = editPageData.indexOf(entity)
                    var temp: EditPageElement?
                    if (index < editPageData.size - 1) {
                        temp = editPageData[index + 1]
                        editPageData[index + 1] = entity
                        editPageData[index] = temp
                        navigation.refreshState(Navigation.MYPAGESTATE)
                    }
                    isActionBarVisibility.value = false

                })
            Text("下一层", modifier = Modifier
                .size(50.dp)
                .clickable {
                    var index = editPageData.indexOf(entity)
                    var temp: EditPageElement?
                    if (index > 0) {
                        temp = editPageData[index - 1]
                        editPageData[index - 1] = entity
                        editPageData[index] = temp
                        navigation.refreshState(Navigation.MYPAGESTATE)
                    }
                    isActionBarVisibility.value = false

                })

        }
    }
    Canvas(
        Modifier
            .offset {
                IntOffset(
                    entity.offsetX.value.roundToInt(),
                    entity.offsetY.value.roundToInt()
                )
            }
            .background(actionBtnBg.value)
            .size(Dp(50f))
            .pointerInput(Unit) {
//                detectDragGestures { change, dragAmount ->
//
//                }
                detectDragGestures({
                    actionBtnBg.value= Color(0xff02f78e)
                }, {
                    actionBtnBg.value= Color(0x0f02f78e)
                }, {
                    actionBtnBg.value= Color(0x0f02f78e)
                }, { change, dragAmount ->

                    change.consumeAllChanges()
                    if (entity.offsetX.value+dragAmount.x-entity.offsetXFrame.value> 40
                        &&entity.offsetX.value+dragAmount.x-entity.offsetXFrame.value< MainActivity.screenW
                        &&entity.offsetY.value - entity.offsetYFrame.value < MainActivity.screenH - 80 * MainActivity.density
                    ) {
                        entity.offsetX.value += dragAmount.x
                    }
                    if(entity.offsetY.value+dragAmount.y-entity.offsetYFrame.value>20
                        &&entity.offsetY.value+dragAmount.y-entity.offsetYFrame.value<MainActivity.screenH){
                        entity.offsetY.value+=dragAmount.y
                    }
                })
            }
            .clickable {
                isActionBarVisibility.value = !isActionBarVisibility.value
            }
    )
    {

        drawIntoCanvas { canvas ->
            val paint = Paint()
            paint.color = Color.Black
            paint.strokeWidth = 3f
            paint.style = PaintingStyle.Stroke
            var rect = Rect(
                left, top, right, bottom
            )
            canvas.drawRect(rect, paint)
//                canvas.drawCircle(Offset(50f,50f),50f,paint)
        }
    }

}