package com.b21cap0397.endf.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.b21cap0397.endf.data.entities.ReportEntity
import com.b21cap0397.endf.utils.AssetHelper

class HomeViewModel : ViewModel() {

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    val text: LiveData<String> = _text
    fun getReports(): List<ReportEntity> {
        return AssetHelper.getReports()
    }
}