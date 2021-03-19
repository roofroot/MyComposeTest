package com.yuxiu.mydiary.ui

import android.opengl.Visibility
import android.os.Bundle
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel


class NaviData(){
    private val misv=MutableLiveData(false);
    val isv: LiveData<Boolean> = misv
    fun onStateChange(visibility:Boolean){
        misv.value=visibility;
    }
}
class Navigation: ViewModel() {
    companion object{
        val MYPAGE = "mypage"
        val SELECTPHOTOLIST = "selectPhotolist"
        val INPUTTEXTPAGE="inputtextpage"
    }

     val map:HashMap<String,NaviData> = HashMap();
    init {
        var navi=NaviData();
        navi.onStateChange(true);
        map.put(MYPAGE,navi)
        navi= NaviData()
        map.put(SELECTPHOTOLIST,navi)
        navi= NaviData()
        map.put(INPUTTEXTPAGE,navi)
    }
     fun onStateChange(name:String,visibility:Boolean){
        map.get(name)?.onStateChange(visibility)
    }
    fun onStateChange(mapdatas:Map<String,Boolean>){
        for (m in mapdatas){
            map.get(m.key)?.onStateChange(m.value)
        }

    }

}