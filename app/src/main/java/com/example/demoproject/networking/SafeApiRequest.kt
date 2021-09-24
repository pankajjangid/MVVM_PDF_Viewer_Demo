package com.example.demoproject.networking

import com.example.demoproject.model.network.ErrorResponse
import com.google.gson.Gson
import org.json.JSONException
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>) : T{

        val response = call.invoke()

        if(response.isSuccessful){

            return response.body()!!
        }else{

            val error = response.errorBody()?.toString()

           val errorResponse = Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)

            val message = StringBuilder()
            error?.let{
                try{
                   message.append(errorResponse.message)
                }catch(e: JSONException){
                    e.printStackTrace()
                }
             //   message.append("\n")
            }
           // message.append("Error Code: ${errorResponse.message}")
            throw ApiException(message.toString())
        }
    }

}
