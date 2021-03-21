package com.yuxiu.mydiary.ui.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.graphics.BitmapCompat
import com.yuxiu.mydiary.MainActivity
import com.yuxiu.mydiary.data.editPageData
import com.yuxiu.mydiary.java.util.ToastUtil
import com.yuxiu.mydiary.java.util.image.AlbumHelper
import com.yuxiu.mydiary.java.util.image.ImageBucket
import com.yuxiu.mydiary.java.util.image.ImageItem
import com.yuxiu.mydiary.ui.Navigation
import dev.chrisbanes.accompanist.glide.GlideImage
import kotlinx.coroutines.launch
import java.io.File


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun selectPhoto(context: Context) {
    var helper = AlbumHelper.getHelper();
    helper.init(context);

    helper.getImagesBucketList(false)

    var imageItems = helper.allImageList
    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 50.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(imageItems.size) { it ->
            itemView(imageItem = imageItems[it])
        }
    }
}

@Composable
fun itemView(imageItem: ImageItem) {
    GlideImage(modifier = Modifier
        .size(50.dp)
        .clickable {
            var entity = MyPageImageEntity()
            entity.imagePath = imageItem.imagePath
            if (imageItem.height > MainActivity.screenH-80*MainActivity.density && imageItem.width > MainActivity.screenW) {
                var bl: Float = (imageItem.height.toFloat() / imageItem.width.toFloat())
                var width: Float = (MainActivity.screenW).toFloat()
                var height: Float = width * bl
                entity.bl = bl
                entity.imageH = mutableStateOf(height / MainActivity.density)
                entity.imageW = mutableStateOf(width / MainActivity.density)
                entity.imageMaxW=width / MainActivity.density
                entity.offsetX = mutableStateOf(width)
                entity.offsetY = mutableStateOf(height)

            } else if (imageItem.height > MainActivity.screenH) {
                var height: Float = (MainActivity.screenH-80*MainActivity.density).toFloat()
                var bl: Float = (imageItem.height.toFloat() / imageItem.width.toFloat())
                var width: Float = height.toFloat() / bl
                entity.bl = bl
                entity.imageH = mutableStateOf(height / MainActivity.density)
                entity.imageW = mutableStateOf(width / MainActivity.density)
                entity.imageMaxW=width / MainActivity.density
                entity.offsetX = mutableStateOf(width)
                entity.offsetY = mutableStateOf(height)
            } else if (imageItem.width > MainActivity.screenW) {
                var bl: Float = (imageItem.height.toFloat() / imageItem.width.toFloat())
                var width: Float = (MainActivity.screenW).toFloat()
                var height: Float = width * bl
                entity.bl = bl
                entity.imageH = mutableStateOf(height / MainActivity.density)
                entity.imageW = mutableStateOf(width / MainActivity.density)
                entity.imageMaxW=width / MainActivity.density
                entity.offsetX = mutableStateOf(width)
                entity.offsetY = mutableStateOf(height)
            } else {
                var height: Float = imageItem.height.toFloat()
                var bl: Float = (imageItem.height.toFloat() / imageItem.width.toFloat())
                var width: Float = imageItem.width.toFloat()
                entity.bl = bl
                entity.imageH = mutableStateOf(height / MainActivity.density)
                entity.imageW = mutableStateOf(width / MainActivity.density)
                entity.imageMaxW=width / MainActivity.density
                entity.offsetX = mutableStateOf(width)
                entity.offsetY = mutableStateOf(height)

            }

            editPageData.add(entity)

//            MainActivity.navigation.onStateChange(Navigation.MYPAGE,true)
            navigation.onStateChange(Navigation.SELECTPHOTOLIST, false)
        }, data = imageItem.imagePath,
        contentScale = ContentScale.Crop,
        contentDescription = "My content description",
        loading = {
            Box(Modifier.matchParentSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        },
        error = {

        }
    )
}