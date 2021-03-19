package com.yuxiu.mydiary.ui.widget

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.yuxiu.mydiary.MainActivity
import dev.chrisbanes.accompanist.glide.GlideImage
import kotlin.math.roundToInt

@Composable
fun getText(entity: MyPageTextEntity) {
    val left: Float = 0f;
    val right: Float = 30f;
    val top: Float = 0f;
    val bottom: Float = 30f;

//    val image = painterResource(R.drawable.placeholder_4_3)
    entity.offsetXFrame = remember {
        mutableStateOf(entity.offsetXFrame.value)
    }
    entity.offsetYFrame = remember {
        mutableStateOf(entity.offsetYFrame.value)
    }
    entity.offsetX = remember {
        mutableStateOf(entity.offsetX.value)
    }
    entity.offsetY = remember {
        mutableStateOf(entity.offsetY.value)
    }


    Text(
        text=entity.textContent,
        color= entity.textColor,
        modifier = Modifier
            .offset {
                IntOffset(
                    entity.offsetXFrame.value.roundToInt(),
                    entity.offsetYFrame.value.roundToInt()
                )
            }
            .border(1.dp, Color.Black)
            .background(entity.bgColor)
            .width(Dp((entity.offsetX.value-entity.offsetXFrame.value)/3))
            .height(Dp((entity.offsetY.value-entity.offsetYFrame.value)/3))
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
//                        if(0<offsetXFrame.value+dragAmount.x) {
                    entity.offsetXFrame.value += dragAmount.x
                    entity.offsetX.value += dragAmount.x
//                        }
//                        if(offsetYFrame.value+dragAmount.y>0){
                    entity.offsetYFrame.value += dragAmount.y
                    entity.offsetY.value += dragAmount.y
//                        }
                }

            },

    )
    Canvas(
        Modifier
            .offset {
                IntOffset(
                    entity.offsetX.value.roundToInt(),
                    entity.offsetY.value.roundToInt()
                )
            }
            .background(Color.Blue)
            .size(Dp(50f))
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    if (entity.offsetX.value+dragAmount.x-entity.offsetXFrame.value> 40
                        &&entity.offsetX.value+dragAmount.x-entity.offsetXFrame.value< MainActivity.screenW
                    ) {
                        entity.offsetX.value += dragAmount.x
                    }
                    if(entity.offsetY.value+dragAmount.y-entity.offsetYFrame.value>20
                        &&entity.offsetY.value+dragAmount.y-entity.offsetYFrame.value<MainActivity.screenH){
                        entity.offsetY.value+=dragAmount.y
                    }
                }
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