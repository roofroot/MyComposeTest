package com.yuxiu.mydiary.ui.widget

import android.util.Log
import androidx.annotation.Px
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
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
import androidx.compose.ui.unit.dp
import com.yuxiu.mydiary.MainActivity
import com.yuxiu.mydiary.R
import dev.chrisbanes.accompanist.glide.GlideImage
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.tan

@Composable
fun getImage(entity: MyPageImageEntity) {
//    val image = painterResource(R.drawable.placeholder_4_3)
    var stateAction = remember {
        mutableStateOf(false)
    }

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
    entity.imageRotate= remember {
        mutableStateOf(entity.imageRotate.value)
    }
    Box(
        Modifier
            .wrapContentHeight()
            .wrapContentHeight()
            .offset {
                IntOffset(
                    entity.offsetXFrame.value.roundToInt(),
                    entity.offsetYFrame.value.roundToInt()
                )
            }
            .pointerInput(Unit) {
                detectDragGestures({
                    entity.isRotate.value=false
                }, {
                }, {
                   
                }, {
                    change, dragAmount ->
                        change.consumeAllChanges()

//                        if(0<offsetXFrame.value+dragAmount.x) {
                        entity.offsetXFrame.value += dragAmount.x
                        entity.offsetX.value += dragAmount.x
//                        }
//                        if(offsetYFrame.value+dragAmount.y>0){
                        entity.offsetYFrame.value += dragAmount.y
                        entity.offsetY.value += dragAmount.y
//                        }
                })


            },){
        GlideImage(
            data = entity.imagePath,
            modifier = Modifier

                .rotate(entity.imageRotate.value)
                .width(Dp(entity.imageW.value))
                .height(Dp(entity.imageH.value)
                )
            ,
            contentScale = ContentScale.Fit,
            contentDescription = "My content description",
            loading = {
                Box(Modifier.matchParentSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

            },
            error = {

            }, shouldRefetchOnSizeChange = { _, size ->

//            Log.e("glide", "${size.height},${size.width}")
                false
            }
        )

    }
        ActionWeight(
            entity = entity,
        )


}
