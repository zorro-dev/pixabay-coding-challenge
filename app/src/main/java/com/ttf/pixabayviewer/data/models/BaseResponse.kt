package com.ttf.pixabayviewer.data.models

import com.google.gson.annotations.SerializedName

open class BaseResponse(

	@field:SerializedName("success")
	val success: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)