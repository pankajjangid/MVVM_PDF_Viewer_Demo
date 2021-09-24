package com.example.demoproject.ui.main.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demoproject.repo.ContentRepository
import com.example.demoproject.utils.Coroutines

class ListViewModel(val repository: ContentRepository) : ViewModel() {


    var listener : ListListener? = null

    var isLoading= MutableLiveData<Boolean>()
    init {
        isLoading.value = false
    }


    fun requestContent(){

        listener?.onStarted()
        Coroutines.io {
            try {

                val response = repository.getContents()

                Coroutines.main {

                    isLoading.value=false
                    listener?.onSuccess(response)

                }
            } catch (e: Exception) {
                e.printStackTrace()
                Coroutines.main {
                    listener?.onFailure(e.message)

                }

            }

        }
    }


    fun onListRefresh(){
        isLoading.value=true

        requestContent()
    }


}