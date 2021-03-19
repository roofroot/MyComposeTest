package com.yuxiu.mydiary.ui.widget

import android.util.Log
import androidx.annotation.Px
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import com.yuxiu.mydiary.MainActivity
import com.yuxiu.mydiary.R
import dev.chrisbanes.accompanist.glide.GlideImage
import kotlin.math.roundToInt

@Composable
fun getImage(entity: MyPageImageEntity) {
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
    entity.imageW = remember {
        mutableStateOf(entity.imageW.value)
    }
    entity.imageH = remember {
        mutableStateOf(entity.imageH.value)
    }

    GlideImage(
        data = entity.imagePath,
        modifier = Modifier

            .offset {
                IntOffset(
                    entity.offsetXFrame.value.roundToInt(),
                    entity.offsetYFrame.value.roundToInt()
                )
            }
            .width(Dp(entity.imageW.value))
            .height(Dp(entity.imageH.value))
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
        contentScale = ContentScale.Fit,
        contentDescription = "My content description",
        loading = {
            Box(Modifier.matchParentSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }

        },
        error = {

        }, shouldRefetchOnSizeChange = { _, size ->

            Log.e("glide", "${size.height},${size.width}")
            false
        }
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
                    if (entity.imageW.value + dragAmount.x / MainActivity.density > 40) {
                        entity.imageW.value += dragAmount.x / MainActivity.density
                        entity.offsetX.value += dragAmount.x
                        entity.imageH.value = entity.imageW.value * entity.bl.value
                        entity.offsetY.value += dragAmount.x * entity.bl.value
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
