package com.yuxiu.mydiary.ui.widget

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.yuxiu.mydiary.MainActivity
import com.yuxiu.mydiary.data.editPageData
import dev.chrisbanes.accompanist.glide.GlideImage
import kotlin.math.roundToInt

public data class PreviewPageEntity(
    var elements:ArrayList<PreviewElementEntity> = arrayListOf(),
)
 open class PreviewElementEntity(
    var offsetXFrame: Float = 0f,
    var offsetYFrame:Float =0f,
    var offsetX: Float = 120f,
    var offsetY: Float = 90f,
)
data class PreviewPageImageEntity(
     var imageW:Float =40f,
     var imageH: Float = 30f,
     var imagePath:String ="",
     var bl:Float=1f):PreviewElementEntity()
data class PreviewTextEntity(
    var textContent:String="",
    var textColor: Color = Color.Black,
    var bgColor: Color = Color.Transparent
):PreviewElementEntity()
@Composable
fun MyPreviewPage(){
    for(data in editPageData){
        if(data is MyPageImageEntity){
            var temp=PreviewPageImageEntity()
            temp.bl=data.bl
            temp.imageH=data.imageH.value
            temp.imageW=data.imageW.value
            temp.offsetX=data.offsetX.value
            temp.offsetY=data.offsetY.value
            temp.offsetXFrame=data.offsetXFrame.value
            temp.offsetYFrame=data.offsetYFrame.value
            temp.imagePath=data.imagePath
            getPreviewImage(entity = temp)
        }
        else if(data is MyPageTextEntity){
            var temp=PreviewTextEntity()
            temp.offsetX=data.offsetX.value
            temp.offsetY=data.offsetY.value
            temp.offsetXFrame=data.offsetXFrame.value
            temp.offsetYFrame=data.offsetYFrame.value
            temp.bgColor=data.bgColor
            temp.textColor=data.textColor
            temp.textContent=data.textContent
            getPreviewText(entity = temp)
        }
    }
}
@Composable
fun getPreviewImage(entity: PreviewPageImageEntity) {
//    val image = painterResource(R.drawable.placeholder_4_3)
    GlideImage(
        data = entity.imagePath,
        modifier = Modifier
            .offset {
                IntOffset(
                    entity.offsetXFrame.roundToInt(),
                    entity.offsetYFrame.roundToInt()
                )
            }
            .width(Dp(entity.imageW))
            .height(Dp(entity.imageH))
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

            Log.e("glide", "${size.height},${size.width}")
            false
        }
    )
}
@Composable
fun getPreviewText(entity:PreviewTextEntity) {
    val left: Float = 0f;
    val right: Float = 30f;
    val top: Float = 0f;
    val bottom: Float = 30f;


    Text(
        text=entity.textContent,
        color= entity.textColor,
        modifier = Modifier
            .offset {
                IntOffset(
                    entity.offsetXFrame.roundToInt(),
                    entity.offsetYFrame.roundToInt()
                )
            }
            .border(1.dp, Color.Black)
            .background(entity.bgColor)
            .width(Dp((entity.offsetX - entity.offsetXFrame) / MainActivity.density))
            .height(Dp((entity.offsetY - entity.offsetYFrame) / MainActivity.density))
            ,

        )
}
