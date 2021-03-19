package com.yuxiu.mydiary

import android.Manifest
import android.content.res.Resources
import android.graphics.Point
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.yuxiu.mydiary.data.Mydata
import com.yuxiu.mydiary.ui.Navigation
import com.yuxiu.mydiary.ui.theme.MyDiaryTheme
import com.yuxiu.mydiary.ui.widget.MyPage
import com.yuxiu.mydiary.ui.widget.selectPhoto
import android.util.DisplayMetrics
import android.view.Display
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Row
import com.yuxiu.mydiary.ui.widget.MyPageTextEntity
import com.yuxiu.mydiary.ui.widget.inputTextPage


class MainActivity : ComponentActivity() {
    companion object {
        var navigation: Navigation = Navigation()
        var screenW = 0;
        var screenH = 0
        var density = 0f;
    }

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screenW = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenH = Resources.getSystem().getDisplayMetrics().heightPixels;
        density = getResources().getDisplayMetrics().density;

        setContent {
            MyDiaryTheme {

                val myPageState = navigation.map.get(Navigation.MYPAGE)?.isv?.observeAsState(false)
                val selectPhotoListState =
                    navigation.map.get(Navigation.SELECTPHOTOLIST)?.isv?.observeAsState(true)
                val inputTextPageState =
                    navigation.map.get(Navigation.INPUTTEXTPAGE)?.isv?.observeAsState(true)
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    myPageState?.let {
                        if (myPageState.value)
//                            AnimatedVisibility(visible = myPageState.value) {
                                MyPage(Mydata.myPageImageData, Mydata.myPageTextData)
//                            }

                    }
                    selectPhotoListState?.let {

                        AnimatedVisibility(visible = selectPhotoListState.value) {
                            selectPhoto(this)
                        }
                    }
                    inputTextPageState?.let {
                        if(inputTextPageState.value)
                            inputTextPage()
                    }
//                    if(!isv.value){
//                        Text("hello")
//                    }
                    Row() {
                        Text("图片", modifier = Modifier.clickable {

                            navigation.onStateChange(Navigation.SELECTPHOTOLIST, true)

                        })
                        Text("文本", modifier = Modifier.clickable {
                            navigation.onStateChange(Navigation.INPUTTEXTPAGE, true)

                        })
                    }


                }
            }
        }
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
//                it->
            //通过的权限
//            val grantedList = it.filterValues { it }.mapNotNull { it.key }
            //是否所有权限都通过
//            val allGranted = grantedList.size == it.size
//            val list = (it - grantedList).map { it.key }
            //未通过的权限
//            val deniedList = list.filter { ActivityCompat.shouldShowRequestPermissionRationale(this, it) }
            //拒绝并且点了“不再询问”权限
//            val alwaysDeniedList = list - deniedList
        }.launch(arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
        ))


    }

}

