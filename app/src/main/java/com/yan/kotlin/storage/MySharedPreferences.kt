package com.yan.kotlin.storage

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject

object MySharedPreferences {
    fun share(context: Context) : SharedPreferences {
        val sharedPreferences = context.getSharedPreferences("date",Context.MODE_PRIVATE)
        return sharedPreferences
    }

    fun getToken(context: Context) : String{
        val loginInfo = share(context).getString("loginInfo",null)
        var jsonObject = JSONObject(loginInfo)
        val token = jsonObject.getString("accessToken")
        return token
    }
}