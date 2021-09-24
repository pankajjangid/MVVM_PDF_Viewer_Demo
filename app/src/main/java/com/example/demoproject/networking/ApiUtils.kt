package com.example.demoproject.networking

import android.app.Activity
import com.example.demoproject.R
import com.example.demoproject.utils.App
import com.example.demoproject.utils.Utils

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object ApiUtils {


    fun handleRetrofitError(e: Throwable) {
        e.printStackTrace()

        when (e) {
            is HttpException -> {
                val responseBody: ResponseBody = e.response()!!.errorBody()!!
                Utils.toast(getErrorMessage(responseBody))
            }
            is SocketTimeoutException -> {

                App.application?.resources?.getString(R.string.msg_socket_time_out)?.let {
                    Utils.toast(
                        it
                    )
                }

            }
            is IOException -> {
                App.application?.resources?.getString(R.string.msg_api_error)?.let {
                    Utils.toast(
                        it
                    )
                }
            }
            else -> {
                App.application?.resources?.getString(R.string.msg_api_error)?.let {
                    Utils.toast(
                        it
                    )
                }
            }
        }
    }


    fun isTimeOut(e: Throwable): Boolean {
        if (e is SocketTimeoutException) {
            return true

        }
        return false
    }

    private fun getErrorMessage(responseBody: ResponseBody): String {
        try {
            val jsonObject = JSONObject(responseBody.string())
            return jsonObject.getString("message")
        } catch (e: Exception) {
            return e.message!!
        }
    }

    fun checkResponseStatus(
        activity: Activity,
        statusCode: Int,
        msg: String?,
        showSuccessMessage: Boolean = false,
        isActivity: Boolean
    ): Boolean {

        var message = ""

        if (msg.isNullOrEmpty()) message = "" else message = msg

        when (statusCode) {
            WebServiceUrl.STATUS_SUCCESS -> {
                if (showSuccessMessage)
                    Utils.toast(message)
                return true
            }
            WebServiceUrl.STATUS_AUTH_FAILED -> {
                handleAuthFailed(activity, message, isActivity)
                return false
            }
            WebServiceUrl.STATUS_PARAMETER_MISSING -> {
                handleServerError(activity, message)
                return false
            }
            WebServiceUrl.STATUS_SESSION_EXPIRE -> {
                handleAuthFailed(activity, message, isActivity)
                return false
            }
            WebServiceUrl.STATUS_SERVER_ERROR -> {
                handleServerError(activity, message)
                return false
            }
            WebServiceUrl.STATUS_SERVER_400 -> {
                return false
            }
            else -> {
                handleServerError(activity, message)

                return false
            }
        }

    }

    private fun handleServerError(activity: Activity, msg: String) {
        Utils.toast(msg)

    }

    private fun handleAuthFailed(activity: Activity, msg: String, isActivity: Boolean) {
        if (msg.isNotEmpty())
            Utils.toast(msg)



        try {


            //logOut(activity)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        /*
        Utils.startNewActivity(activity,LogOutActivity::class.java)
*/


    }


}
