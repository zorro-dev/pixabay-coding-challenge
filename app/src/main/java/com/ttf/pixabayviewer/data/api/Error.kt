package com.ttf.pixabayviewer.data.api

import com.google.gson.Gson
import retrofit2.Response

data class Error(val code: ErrorCode, val message: String? = null)

enum class ErrorCode(val code: String) {
    UNKNOWN("unknown"),
    AUTH("auth"),
    NOT_FOUND("not_found"),
    PHONE("phone"),
    PASSWORD("password"),
    EMAIL("email"),
    DETAIL("detail");

    companion object {
        fun from(code: String): ErrorCode = values().first { it.code == code }
    }
}

object ErrorUtils {

    fun parseError(response: Response<*>): Error? {
        return try {
            when (response.code()) {
                401, 403 -> Error(ErrorCode.AUTH, "Something went wrong (Auth)")
                404 -> Error(ErrorCode.NOT_FOUND, "Something went wrong (Unknown)")
                else -> Error(ErrorCode.UNKNOWN,
                    Gson().fromJson(response.errorBody()?.string(), Error::class.java)?.message
                        ?: "Something went wrong")
            }
        } catch (e: Exception) {
            Error(ErrorCode.UNKNOWN, e.message)
        }
    }
}