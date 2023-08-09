package com.eric.composearch.utils

import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(call: suspend () -> T): T? {
    return try {
        withContext(Dispatchers.IO) {
            call()
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        e.printStackTrace()
        Log.e(APP_DEBUG_TAG, parseErrorMsg(e))
        null
    }
}

fun parseErrorMsg(e: Throwable): String {
    if (e is SocketTimeoutException) {
        return "Network Timeout"
    } else if (e is UnknownHostException) {
        return "Network connection is not available. Please try again later"
    }

    val httpException = e as? HttpException
    var errorMsg = "request fail"
    val errorBody = httpException?.response()?.errorBody()
    errorBody?.let {
        try {
            val errorJson = JSONObject(it.string())
            if (errorJson.has("msg")) {
                val message = errorJson.getString("msg")
                if (message != "null") {
                    errorMsg = message
                }
            }
        } catch (e: Exception) {
        }
    }
    return errorMsg
}