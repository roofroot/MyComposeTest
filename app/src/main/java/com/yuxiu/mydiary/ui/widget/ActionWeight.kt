package com.yuxiu.mydiary.ui.widget

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.vector.DefaultRotation
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.resource.bitmap.Rotate
import com.yuxiu.mydiary.MainActivity
import com.yuxiu.mydiary.data.editPageData
import com.yuxiu.mydiary.ui.Navigation
import kotlin.math.*
var  ssz= -1;
@SuppressLint("RememberReturnType")
@Composable
fun ActionWeight(entity: MyPageImageEntity) {

    val left: Float = 0f;
    val right: Float = 30f;
    val top: Float = 0f;
    val bottom: Float = 30f;
    var center: Float;
    var isActionBarVisibility = remember {
        mutableStateOf(false)
    }
    var actionBtnBg = remember {
        mutableStateOf(Color(0x0fff3e96))
    }
    entity.isRotate = remember {
        mutableStateOf(false)
    }

    var ra = remember {
        mutableStateOf(0f)
    }
    var rb = remember {
        mutableStateOf(0f)
    }


    var rc = remember {
        mutableStateOf(0f)
    }
    var rd = remember {
        mutableStateOf(0f)
    }
    var xdistance= remember {
        mutableStateOf(0f)
    }
    var ydistance= remember {
        mutableStateOf(0f)
    }




//        var circleR = 0f;
//        if (entity.imageW.value > entity.imageH.value) {
//            circleR = entity.imageW.value / 2
//        } else {
//            circleR = entity.imageH.value / 2
//        }





    if (isActionBarVisibility.value) {

        Column(Modifier
            .offset {
                IntOffset(
                    entity.offsetX.value.roundToInt(),
                    entity.offsetY.value.roundToInt() - (MainActivity.density * 150).toInt()
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

            Text("旋转", modifier = Modifier
                .size(50.dp)
                .clickable {
                    isActionBarVisibility.value = false
                    var rotateRandians = Math.toRadians(entity.imageRotate.value.toDouble())
                    entity.isRotate .value = true
                    if ( entity.isRotate .value) {
                        ra.value =
                            (entity.offsetX.value - entity.offsetXFrame.value) / 2 + entity.offsetXFrame.value
                        rb.value =
                            (entity.offsetY.value - entity.offsetYFrame.value) / 2 + entity.offsetYFrame.value
                        var height = entity.imageH.value * MainActivity.density
                        var width = entity.imageW.value * MainActivity.density

                        /**
                         *  这段是计算右下角与中心点y轴距离
                         *  分两种情况 一种情况 是temp等于这个距离
                         *  另一种情况是temp小于这个距离
                         */
                        if(entity.imageRotate.value==0f){
                            rc.value= entity.offsetX.value
                            rd.value=entity.offsetY.value

                        }else {

                            var temp = (width / (sin(rotateRandians) * 2)).toFloat()
                            var tempb =
                                (((height / 2 - cos(rotateRandians) * temp)) * cos(rotateRandians)).toFloat()

                            if(rotateRandians<30){
                                var xtemp=(width/(2*tan(rotateRandians))-height/2).toFloat()
                                var temp=(width / (sin(rotateRandians) * 2)).toFloat()
                                rd.value = (rb.value + temp -xtemp* cos(rotateRandians)).toFloat()
                                rc.value=(ra.value+xtemp*sin(rotateRandians)).toFloat()
                            }else {
                            if (temp * temp >= (width / 2 * width / 2 + height / 2 * height / 2)) {
                                var tempc =
                                    ((height / 2 - cos(rotateRandians) * tempb) / cos(rotateRandians)).toFloat()
                                Log.e("tempc", "$tempc")
                                rd.value = rb.value + temp
                                rc.value = ra.value - tempc
                            } else {
                                var tempc =
                                    (((height / 2 - cos(rotateRandians) * temp)) * sin(
                                        rotateRandians)).toFloat()
                                Log.e("tempc", "$tempc")
                                rd.value = rb.value + temp + tempb
                                rc.value = ra.value - tempc
                            }
                        }
                    }

                }})

                }
        }
    if( entity.isRotate .value){
        Box(Modifier
            .offset {
                IntOffset(
                    ra.value.roundToInt(),
                    rb.value.roundToInt(),
                )
            }
            .size(10.dp)
            .background(Color.Red)

        ) {

        }

        Box(Modifier
            .offset {
                IntOffset(
                    rc.value.roundToInt(),
                    rd.value.roundToInt(),
                )
            }
            .size(50.dp)
            .background(Color.Red)
            .pointerInput(Unit) {
//                detectDragGestures { change, dragAmount ->
//
//                }
                detectDragGestures({
                    ssz=-1
                    actionBtnBg.value = Color(0xffff3e96)
                }, {
                    actionBtnBg.value = Color(0x0fff3e96)
                    entity.isRotate.value=false
                }, {
                    actionBtnBg.value = Color(0x0fff3e96)
                    entity.isRotate.value=false
                }, { change, dragAmount ->
                    change.consumeAllChanges()
                    ra.value =
                        (entity.offsetX.value - entity.offsetXFrame.value) / 2 + entity.offsetXFrame.value
                    rb.value =
                        (entity.offsetY.value - entity.offsetYFrame.value) / 2 + entity.offsetYFrame.value
                    var height = entity.imageH.value * MainActivity.density
                    var width = entity.imageW.value * MainActivity.density
                    /**
                     *  这段是计算右下角与中心点y轴距离
                     *  分两种情况 一种情况 是temp等于这个距离
                     *  另一种情况是temp小于这个距离
                     */
//                    if(dragAmount.x<0){
                            xdistance.value -= dragAmount.x

//                    }else {
//                            xdistance.value += dragAmount.x
//                    }
                    xdistance.value=xdistance.value%(height*8)
                    var x =0f
                       x = xdistance.value * 90 / height;
                    entity.imageRotate.value=x
//                    if(dragAmount.x<0) {
//                        entity.imageRotate.value = x
//                    }else{
//                        entity.imageRotate.value=-x
//                    }
                    var rotateRandians =Math.toRadians(x.toDouble())
                    Log.e("dis","${dragAmount.y}，${ydistance.value},${xdistance.value},$ssz")

                if(rotateRandians<30){
                    var xtemp=(width/(2*tan(rotateRandians))-height/2).toFloat()
                    var temp=(width / (sin(rotateRandians) * 2)).toFloat()
                    rd.value = (rb.value + temp -xtemp* cos(rotateRandians)).toFloat()
                    rc.value=(ra.value+xtemp*sin(rotateRandians)).toFloat()
                }else {
                    var temp = (width / (sin(rotateRandians) * 2)).toFloat()
                    var tempb =
                        (((height / 2 - cos(rotateRandians) * temp)) * cos(rotateRandians)).toFloat()
                    if (temp * temp >= (width / 2 * width / 2 + height / 2 * height / 2)) {
                        var tempc =
                            ((height / 2 - cos(rotateRandians) * tempb) / cos(rotateRandians)).toFloat()
                        Log.e("tempc", "$tempc")
                        rd.value = rb.value + temp
                        rc.value = ra.value - tempc
                    } else {
                        var tempc =
                            (((height / 2 - cos(rotateRandians) * temp)) * sin(rotateRandians)).toFloat()
                        Log.e("tempc", "$tempc")
                        rd.value = rb.value + temp + tempb
                        rc.value = ra.value - tempc

                    }
                }

                })

            }
            .clickable {
                isActionBarVisibility.value = !isActionBarVisibility.value
            }

        ) {


        }
    }else {
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

                        actionBtnBg.value = Color(0xffff3e96)
                    }, {
                        actionBtnBg.value = Color(0x0fff3e96)
                    }, {
                        actionBtnBg.value = Color(0x0fff3e96)
                    }, { change, dragAmount ->

                        change.consumeAllChanges()
                        if (dragAmount.x > 0 && entity.offsetX.value - entity.offsetXFrame.value < MainActivity.screenW
                            && entity.offsetY.value - entity.offsetYFrame.value < MainActivity.screenH - 80 * MainActivity.density
                        ) {
                            entity.imageW.value += ((dragAmount.x / MainActivity.density))
                            entity.offsetX.value += dragAmount.x
                            entity.imageH.value = entity.imageW.value * entity.bl
                            entity.offsetY.value =
                                entity.offsetYFrame.value + entity.imageH.value * MainActivity.density
                        }

                        if (dragAmount.x < 0 && entity.imageW.value + dragAmount.x / MainActivity.density > 40) {
                            entity.imageW.value += ((dragAmount.x / MainActivity.density))
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



//        Canvas(
//            Modifier
//                .offset {
//                    IntOffset(
//                        entity.offsetX.value.roundToInt(),
//                        entity.offsetY.value.roundToInt()
//                    )
//                }
//                .background(actionBtnBg.value)
//                .size(Dp(50f))
//                .clip(RoundedCornerShape(25f))
//                .pointerInput(Unit) {
////                detectDragGestures { change, dragAmount ->
////
////                }
//                    detectDragGestures({
//                        actionBtnBg.value = Color(0xffff3e96)
//                    }, {
//                        actionBtnBg.value = Color(0x0fff3e96)
//                    }, {
//                        actionBtnBg.value = Color(0x0fff3e96)
//                    }, { change, dragAmount ->
//
//                        change.consumeAllChanges()
//                        if (dragAmount.x > 0 && entity.offsetX.value - entity.offsetXFrame.value < MainActivity.screenW
//                            && entity.offsetY.value - entity.offsetYFrame.value < MainActivity.screenH - 80 * MainActivity.density
//                        ) {
//                            entity.imageW.value += (dragAmount.x / MainActivity.density)
//                            entity.offsetX.value += dragAmount.x
//                            entity.imageH.value = entity.imageW.value * entity.bl
//                            entity.offsetY.value =
//                                entity.offsetYFrame.value + entity.imageH.value * MainActivity.density
//                        }
//
//                        if (dragAmount.x < 0 && entity.imageW.value + dragAmount.x / MainActivity.density > 40) {
//                            entity.imageW.value += dragAmount.x / MainActivity.density
//                            entity.offsetX.value += dragAmount.x
//                            entity.imageH.value = entity.imageW.value * entity.bl
//                            entity.offsetY.value =
//                                entity.offsetYFrame.value + entity.imageH.value * MainActivity.density
//                        }
//                    })
//                }
//                .clickable {
//                    isActionBarVisibility.value = !isActionBarVisibility.value
//                }
//        )
//        {
//
//            drawIntoCanvas { canvas ->
//                val paint = Paint()
//                paint.color = Color.Black
//                paint.strokeWidth = 3f
//                paint.style = PaintingStyle.Stroke
//                var rect = Rect(
//                    left, top, right, bottom
//                )
//                canvas.drawRect(rect, paint)
////                canvas.drawCircle(Offset(50f,50f),50f,paint)
//            }
//        }
//    }

}

@Composable
fun ActionWeight(entity: MyPageTextEntity) {

    val left: Float = 0f;
    val right: Float = 30f;
    val top: Float = 0f;
    val bottom: Float = 30f;
    var isActionBarVisibility = remember {
        mutableStateOf(false)
    }
    var actionBtnBg = remember {
        mutableStateOf(Color(0x0f02f78e))
    }
    if (isActionBarVisibility.value) {
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
                    actionBtnBg.value = Color(0xff02f78e)
                }, {
                    actionBtnBg.value = Color(0x0f02f78e)
                }, {
                    actionBtnBg.value = Color(0x0f02f78e)
                }, { change, dragAmount ->

                    change.consumeAllChanges()
                    if (entity.offsetX.value + dragAmount.x - entity.offsetXFrame.value > 40
                        && entity.offsetX.value + dragAmount.x - entity.offsetXFrame.value < MainActivity.screenW
                        && entity.offsetY.value - entity.offsetYFrame.value < MainActivity.screenH - 80 * MainActivity.density
                    ) {
                        entity.offsetX.value += dragAmount.x
                    }
                    if (entity.offsetY.value + dragAmount.y - entity.offsetYFrame.value > 20
                        && entity.offsetY.value + dragAmount.y - entity.offsetYFrame.value < MainActivity.screenH
                    ) {
                        entity.offsetY.value += dragAmount.y
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