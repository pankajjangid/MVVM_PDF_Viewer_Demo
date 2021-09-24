package com.example.demoproject.repo

import com.example.demoproject.model.network.ContentResponse
import com.example.demoproject.networking.MyApi
import com.example.demoproject.networking.SafeApiRequest

class ContentRepository(private val myApi: MyApi) : SafeApiRequest() {


    suspend fun getContents(): ContentResponse {
        return apiRequest { myApi.getContentData() }
    }


}