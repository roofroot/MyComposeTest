package com.yuxiu.mydiary.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
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
            .width(Dp((entity.offsetX.value - entity.offsetXFrame.value) / MainActivity.density))
            .height(Dp((entity.offsetY.value - entity.offsetYFrame.value) / MainActivity.density))
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

   ActionWeight(entity = entity)

}