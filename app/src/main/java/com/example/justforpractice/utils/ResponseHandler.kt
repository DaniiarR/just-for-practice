package com.example.justforpractice.utils

import android.util.Log
import java.net.ConnectException
import java.net.SocketTimeoutException

open class ResponseHandler {

    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        Log.e("ERROR",e.toString())
        return when (e) {
            is SocketTimeoutException -> Resource.error(getErrorMessage(1432), null)
            is ConnectException -> Resource.error("Не удалось подключиться к серверу", null)
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            1432 -> "Timeout"
            401 -> "Unauthorised"
            404 -> "Пользователь не найден"
            else -> "Что-то пошло не так"
        }
    }

//    private fun convertErrorBody(throwable: HttpException): String {
////        Log.e("RESP",throwable.message()+" - "+throwable.response().toString()+" - " + throwable.response()?.errorBody()?.string().toString())
//        throwable.response()?.errorBody()?.string().toString().let {
//            try {
//                Log.e("STRING",it+"kkk")
//                val json = JSONObject(it)
//                json.keys().forEach {s->
//                    return JsonParser().parse(it).asJsonObject[s].asString
//                }
//            } catch (e: Exception) {
//
//            }
//        }
//        return "Что-то пошло не так"
//    }
}