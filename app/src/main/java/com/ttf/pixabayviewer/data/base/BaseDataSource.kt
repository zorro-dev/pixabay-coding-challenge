package com.ttf.pixabayviewer.data.base


import com.ttf.pixabayviewer.data.api.ErrorCode
import com.ttf.pixabayviewer.data.api.IResult
import com.ttf.pixabayviewer.data.models.BaseResponse
import retrofit2.Response
import com.ttf.pixabayviewer.data.api.Error
import com.ttf.pixabayviewer.data.api.ErrorUtils

open class BaseDataSource {

    protected suspend fun <T> getResponse(
        request: suspend () -> Response<T>,
        defaultErrorMessage: String
    ): IResult<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                if (result.body() is BaseResponse && (result.body() as BaseResponse).success == 0) {
                    IResult.error(
                        Error(
                            ErrorCode.UNKNOWN,
                            (result.body() as BaseResponse).message ?: defaultErrorMessage
                        )
                    )
                } else {
                    return IResult.success(result.body())
                }
            } else {
                val errorResponse = ErrorUtils.parseError(result)
                IResult.error(errorResponse)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            IResult.error(Error(ErrorCode.UNKNOWN, e.message ?: defaultErrorMessage))
        }
    }

}