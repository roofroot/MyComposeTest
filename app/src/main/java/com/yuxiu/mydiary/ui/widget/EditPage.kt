package com.yuxiu.mydiary.ui.widget

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yuxiu.mydiary.data.editPageData
import com.yuxiu.mydiary.ui.Navigation

var navigation = Navigation();

@ExperimentalAnimationApi
@Composable
fun EditPage(context: Context) {

    val selectPhotoListState =
        navigation.map.get(Navigation.SELECTPHOTOLIST)?.isv?.observeAsState(false)

    val inputTextPageState =
        navigation.map.get(Navigation.INPUTTEXTPAGE)?.isv?.observeAsState(false)

    val myPageState = navigation.map.get(Navigation.MYPAGESTATE)?.isv?.observeAsState(true)

    myPageState?.value?.let {
        if (myPageState.value) {
            MyPage(editPageData)
        }else{
            MyPage(editPageData)
        }
    }


    selectPhotoListState?.let {
        AnimatedVisibility(
            visible = selectPhotoListState.value,
            enter = slideInVertically(
                initialOffsetY = { -40 }
            ) + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            selectPhoto(context)
        }
    }
    inputTextPageState?.let {
        AnimatedVisibility(
            visible = inputTextPageState.value,
            enter = slideInVertically(
                initialOffsetY = { -40 }
            ) + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            inputTextPage()
        }
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        Text("图片", modifier = Modifier.clickable {
            navigation.onStateChange(Navigation.SELECTPHOTOLIST, true)
        })
        Text("文本", modifier = Modifier.clickable {
            navigation.onStateChange(Navigation.INPUTTEXTPAGE, true)
        })
        Text("视频", modifier = Modifier.clickable {
            var mediaEntity=MyPageMediaEntity(player=null)
            mediaEntity.mediaPath="rtmp://58.200.131.2:1935/livetv/hunantv"
            editPageData.add(mediaEntity)
//            mediaEntity=MyPageMediaEntity()
//            mediaEntity.mediaPath="http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
//            editPageData.add(mediaEntity)
            navigation.refreshState(Navigation.MYPAGESTATE)


        })
    }
}

