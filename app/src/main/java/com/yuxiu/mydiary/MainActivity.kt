package com.yuxiu.mydiary

import android.Manifest
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.yuxiu.mydiary.ui.Navigation
import com.yuxiu.mydiary.ui.theme.MyDiaryTheme
import android.util.DisplayMetrics
import android.view.Display
import android.view.animation.TranslateAnimation
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.yuxiu.mydiary.ui.PageName
import com.yuxiu.mydiary.ui.widget.*


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
            Column(Modifier
                .fillMaxHeight()
                .fillMaxWidth()) {
                val currentPage = navigation.currentPage.observeAsState(PageName.EDITPAGE)
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()){
                    Crossfade(targetState = currentPage.value,
                        modifier = Modifier
                            .fillMaxSize(),
                        animationSpec = ChangePage()) {
                        MyDiaryTheme {
                            when (currentPage.value) {
                                PageName.EDITPAGE -> {
                                    EditPage(this@MainActivity)
                                }
                                PageName.SHOWPAGE -> {
                                    MyPreviewPage()
                                }

                            }
                        }
                    }
                }

                var tab1Select = remember {
                    mutableStateOf(true)
                }
                var tab2Select = remember {
                    mutableStateOf(false)
                }
                var selectIndex=remember{
                    mutableStateOf(0)
                }

                TabRow(selectedTabIndex = selectIndex.value, modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)) {
                    Tab(selected = tab1Select.value, {
                        tab1Select.value = true;
                        tab2Select.value = false;
                        selectIndex.value=0;
                        navigation.changeCurrentPage(PageName.EDITPAGE)
                    }) {
                        Text("编辑页面")
                    }
                    Tab(selected = tab2Select.value, {
                        tab1Select.value = false;
                        tab2Select.value = true;
                        selectIndex.value=1
                        navigation.changeCurrentPage(PageName.SHOWPAGE)
                    }) {
                        Text("预览页面")
                    }

                }

                // A surface container using the 'background' color from the theme
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

    fun <T> ChangePage(
        durationMillis: Int = 300,
        delayMillis: Int = 0,
        easing: Easing = FastOutLinearInEasing,
    ): TweenSpec<T> = TweenSpec(durationMillis, delayMillis, easing)

}

