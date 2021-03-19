package com.yuxiu.mydiary.ui.widget

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.yuxiu.mydiary.MainActivity
import com.yuxiu.mydiary.R
import com.yuxiu.mydiary.data.Mydata
import com.yuxiu.mydiary.ui.Navigation
import java.lang.reflect.Array
import kotlin.math.roundToInt

data class InputColorEntity(var color: Color = Color.Black, var name: String = "")

@Composable
fun inputTextPage() {
    var fgColor: MutableState<Color> = remember {
        mutableStateOf(Color.Black)
    }
    var bgColor: MutableState<Color> = remember {
        mutableStateOf(Color.Transparent)
    }

    var myPageTextEntity = MyPageTextEntity()
    var content: MutableState<String> = remember { mutableStateOf("") }
    Column (Modifier.background(Color.White).fillMaxWidth().fillMaxHeight()){
        Box(modifier = Modifier.fillMaxWidth().background(Color.White)
            .height(Dp((MainActivity.screenH - 1000) / MainActivity.density)) ){
//            Image(
//                contentDescription = "",
//                painter = painterResource(id = R.mipmap.transbg),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight()
//
//            )
            TextField(value = content.value,
                textStyle = TextStyle(color = fgColor.value),
                onValueChange = { it ->

                    content.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight().background(color = bgColor.value))
        }

        var colorList = ArrayList<InputColorEntity>()
        var inputTextColor = InputColorEntity()
        inputTextColor.color = Color.White
        colorList.add(inputTextColor)
        inputTextColor = InputColorEntity()
        inputTextColor.color = Color.Green
        colorList.add(inputTextColor)
        inputTextColor = InputColorEntity()
        inputTextColor.color = Color.Blue
        colorList.add(inputTextColor)
        inputTextColor = InputColorEntity()
        inputTextColor.color = Color.Cyan
        colorList.add(inputTextColor)
        inputTextColor = InputColorEntity()
        colorList.add(inputTextColor)
//        LazyRow(modifier = Modifier.padding(end = 16.dp)) {
//            items(colorList.size) { index ->
//                Box(modifier = Modifier
//                    .size(30.dp)
//                    .background(colorList[index].color)
//                    .clickable {
//                        myPageTextEntity.textColor = colorList[index].color
////                        MainActivity.navigation.onStateChange(Navigation.INPUTTEXTPAGE,false)
//                    }
//                )
//            }
//        }
        PaletteWidget(fgColor,bgColor)
        Box(modifier = Modifier
            .height(30.dp)
            .width(60.dp)
            .border(1.dp, Color.Gray)
            .background(Color.White)
            .clickable {
                myPageTextEntity.textColor =Color(colorArr[0], colorArr[1], colorArr[2], colorArr[3])
                myPageTextEntity.bgColor=Color(colorArrBg[0], colorArrBg[1], colorArrBg[2], colorArrBg[3])
                Mydata.myPageTextData.add(myPageTextEntity)
                MainActivity.navigation.onStateChange(Navigation.INPUTTEXTPAGE, false)
                myPageTextEntity.textContent = content.value
            }) {
            Text(text = "确定")
        }

    }

}
var colorArr=arrayOf(0, 0, 0, 255)
var colorArrBg= arrayOf(0,0,0,0)
@Composable
fun PaletteWidget(fgColor: MutableState<Color>,bgColor: MutableState<Color>) {

    var isBg:MutableState<Boolean> = remember {
        mutableStateOf(false)
    };
    Column (Modifier.background(Color.Gray)){
        Row(Modifier
            .width(200.dp)
            .height(100.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier
                .background(color = fgColor.value, shape = CircleShape)
                .size(80.dp)
                .clickable {
                    isBg.value = false;
                }) {

            }

            Box(modifier = Modifier
                .background(color = bgColor.value, shape = CircleShape)
                .size(80.dp)
                .clickable {
                    isBg.value = true;
                }
                .padding(10.dp)) {

            }

        }

        if(isBg.value){
            colorBar({
                colorArrBg[0] = it;
                bgColor.value = Color(colorArrBg[0], colorArrBg[1], colorArrBg[2], colorArrBg[3])
            }, listOf(Color(0, 0, 0, 255), Color(255, 0, 0, 255)),
                listOf(0f, MainActivity.screenW.toFloat()), colorArrBg[0])
            colorBar(
                {
//                    Log.e("it", "$it")
                    colorArrBg[1] = it
                    bgColor.value = Color(colorArrBg[0], colorArrBg[1], colorArrBg[2], colorArrBg[3])

                }, listOf(Color(0, 0, 0, 255), Color(0, 255, 0, 255)),
                listOf(0f, MainActivity.screenW.toFloat()),colorArrBg[1])
            colorBar({
                colorArrBg[2] = it;
                bgColor.value = Color(colorArrBg[0], colorArrBg[1], colorArrBg[2], colorArrBg[3])
            }, listOf(Color(0, 0, 0, 255), Color(0, 0, 255, 255)),
                listOf(0f, MainActivity.screenW.toFloat()),colorArrBg[2])

            colorBar({
                colorArrBg[3] = 255 - it;
                bgColor.value = Color(colorArrBg[0], colorArrBg[1], colorArrBg[2], colorArrBg[3])
            }, listOf(Color(255, 255, 255, 255), Color(255, 255, 255, 0)),
                listOf(0f, MainActivity.screenW.toFloat()),255-colorArrBg[3])
        }else {
//            Log.e("arr", "${colorArr[0]}+${colorArr[1]}+${colorArr[2]}+${colorArr[3]}")
            colorBar({
//                Log.e("it" ,"$it");
                colorArr[0] = it;
//                Log.e("arrin", "${colorArr[0]}+${colorArr[1]}+${colorArr[2]}+${colorArr[3]}")
                fgColor.value = Color(colorArr[0], colorArr[1], colorArr[2], colorArr[3])
            }, listOf(Color(0, 0, 0, 255), Color(255, 0, 0, 255)),
                listOf(0f, MainActivity.screenW.toFloat()),colorArr[0])
            colorBar(
                {
                    Log.e("it", "$it")
                    colorArr[1] = it
                    fgColor.value = Color(colorArr[0], colorArr[1], colorArr[2], colorArr[3])

                }, listOf(Color(0, 0, 0, 255), Color(0, 255, 0, 255)),
                listOf(0f, MainActivity.screenW.toFloat()),colorArr[1])
            colorBar({
                Log.e("it", "$it")
                colorArr[2] = it;
                fgColor.value = Color(colorArr[0], colorArr[1], colorArr[2], colorArr[3])
            }, listOf(Color(0, 0, 0, 255), Color(0, 0, 255, 255)),
                listOf(0f, MainActivity.screenW.toFloat()),colorArr[2])

            colorBar({
                Log.e("it", "$it")
                colorArr[3] = 255 - it;
                fgColor.value = Color(colorArr[0], colorArr[1], colorArr[2], colorArr[3])
            }, listOf(Color(255, 255, 255, 255), Color(255, 255, 255, 0)),
                listOf(0f, MainActivity.screenW.toFloat()),255-colorArr[3])
        }
    }
}

@Composable
fun colorBar(callback: (color: Int) -> Unit, listColor: List<Color>, listStop: List<Float>,cinit:Int) {
    var offset=((MainActivity.screenW - 46* MainActivity.density)*cinit.toFloat())/255f
    Log.e("offset", "$cinit")
    var offsetX: MutableState<Float> = remember {
        mutableStateOf(offset)
    }
    Box(modifier = Modifier.height(40.dp)) {
        Box(modifier = Modifier
            .height(30.dp)
            .fillMaxWidth()) {
            Canvas(modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .padding(top = 5.dp)
            ) {
                drawIntoCanvas { canvas ->
                    val paint = Paint()
                    paint.strokeWidth = 3f
                    paint.shader =
                        getGradientShader(listColor, listStop)
                    paint.style = PaintingStyle.Fill
                    var rect = Rect(
                        0f, 0f, MainActivity.screenW.toFloat(), 30 * MainActivity.density
                    )
                    canvas.drawRect(rect, paint)

//                canvas.drawCircle(Offset(50f,50f),50f,paint)
                }
            }
        }
        Image(
            contentDescription = "",
            painter = painterResource(id = R.mipmap.mark1),
            modifier = Modifier
                .offset((offsetX.value / MainActivity.density).dp,
                    0.dp)
                .size(40.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        if (offsetX.value + dragAmount.x <= MainActivity.screenW - 45 * MainActivity.density && offsetX.value + dragAmount.x > 0) {
                            offsetX.value += dragAmount.x
                            var color =
                                ((offsetX.value.toInt() / (MainActivity.screenW - 46 * MainActivity.density)) * 255).toInt();
                            Log.e("color", "$color")
                            callback(color)

                        }

                    }
                }
        )

    }

}

fun getGradientShader(listColor: List<Color>, listStop: List<Float>): Shader {
    return LinearGradientShader(Offset.Zero,
        Offset(MainActivity.screenW.toFloat(), 0f),
        listColor, listStop, TileMode.Clamp)
}