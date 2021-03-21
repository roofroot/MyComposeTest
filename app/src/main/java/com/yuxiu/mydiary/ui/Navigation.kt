package com.yuxiu.mydiary.ui

import android.graphics.pdf.PdfDocument
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.yuxiu.mydiary.ui.widget.navigation

enum class PageName{
    EDITPAGE,SHOWPAGE
}

class NaviData() {
    val isv = MutableLiveData(false);
    fun onStateChange(visibility: Boolean) {
        isv.value = visibility;
    }

}

class Navigation  {
    companion object {
        val MYPAGE = "mypage"
        val SELECTPHOTOLIST = "selectPhotolist"
        val INPUTTEXTPAGE = "inputtextpage"
        val MYPAGESTATE="mypagestate"
    }

    val currentPage = MutableLiveData<PageName>()
    val map: HashMap<String, NaviData> = HashMap();

    init {
        var navi = NaviData();
        map.put(MYPAGE, navi)
        navi = NaviData()
        map.put(SELECTPHOTOLIST, navi)
        navi = NaviData()
        map.put(INPUTTEXTPAGE, navi)
        navi= NaviData()
        map.put(MYPAGESTATE,navi)
    }

    fun onStateChange(name: String, visibility: Boolean) {
        map.get(name)?.onStateChange(visibility)
    }
    fun refreshState(key:String)
    {
        var bool=map.get(key)?.isv?.value;
        bool?.let {
            map.get(key)?.isv?.value=!bool
        }
    }
    fun changeCurrentPage(page: PageName){
        currentPage.value=page;
    }
    fun onStateChange(mapdatas: Map<String, Boolean>) {
        for (m in mapdatas) {
            map.get(m.key)?.onStateChange(m.value)
        }
    }

}