package com.example.demoproject.ui.main.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailsViewModel : ViewModel() {



    var mediaTitleCustom = MutableLiveData<String>()
    var mediaType = MutableLiveData<String>()
    var dateString = MutableLiveData<String>()

    init {


        mediaTitleCustom.value = ""
        mediaType.value = ""
        dateString.value = ""
    }
}