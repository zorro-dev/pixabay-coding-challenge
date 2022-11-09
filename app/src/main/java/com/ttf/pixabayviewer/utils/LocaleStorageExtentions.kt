package com.ttf.pixabayviewer.utils

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

fun <T> SharedPreferences.writeList(key: String, items: List<T>) {
    this.edit().putString(key, Gson().toJson(items, object : TypeToken<List<T>?>() {}.type)).apply()
}

fun <T> SharedPreferences.readList(key: String, type: Class<T>): List<T> {
    return try {
        Gson().fromJson(this.getString(key, ""), ListOfSomething(type))
    } catch (e: Exception) {
        emptyList()
    }
}

fun SharedPreferences.writeObject(key: String, value: Any) {
    this.edit().putString(key, Gson().toJson(value)).apply()
}

@SuppressLint("ApplySharedPref")
fun SharedPreferences.writeObjectImmediately(key: String, value: Any) {
    this.edit().putString(key, Gson().toJson(value)).commit()
}

fun <T> SharedPreferences.readObject(key: String, type: Class<T>): T? {
    return Gson().fromJson(this.getString(key, ""), type)
}

internal class ListOfSomething<X>(wrapped: Class<X>) : ParameterizedType {

    private val wrapped: Class<*> = wrapped

    override fun getActualTypeArguments(): Array<Type> {
        return arrayOf(wrapped)
    }

    override fun getRawType(): Type {
        return MutableList::class.java
    }

    override fun getOwnerType(): Type? {
        return null
    }
}