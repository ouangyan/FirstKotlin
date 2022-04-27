package com.yan.kotlin.manager

import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import java.util.*

object SharedPreferencesManager {
    var context : Context? = null

    fun init(context: Context){
        this.context = context
    }

    fun destroy(){
        this.context = null
    }

    fun contains(sharedPreferencesName: String,storeKey: String) : Boolean{
        val sharedPreferences =
            context!!.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        return sharedPreferences.contains(storeKey)
    }

    fun putString(sharedPreferencesName: String, storeKey: String, storeValue: String) {
        putString(sharedPreferencesName, storeKey, storeValue, -1)
    }

    fun putString(
        sharedPreferencesName: String?,
        storeKey: String?,
        storeValue: String?,
        timeoutMilliseconds: Int
    ) {
        try {
            val valueObject = JSONObject()
            valueObject.put("value", storeValue)
            if (timeoutMilliseconds > 0) {
                val currentTime = Date()
                valueObject.put("createTime", currentTime.time)
                valueObject.put("timeout", timeoutMilliseconds)
            }
            val sharedPreferences =
                context!!.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(storeKey, valueObject.toString())
            editor.apply()
        } catch (ex: JSONException) {
            //
        }
    }

    fun getString(sharedPreferencesName: String?, storeKey: String?): String? {
        val sharedPreferences =
            context!!.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        val storeValue = sharedPreferences.getString(storeKey, null) ?: return null
        return try {
            val valueObject = JSONObject(storeValue)
            if (valueObject.has("createTime")
                && valueObject.has("timeout")
            ) {
                val createTime = valueObject.getLong("createTime")
                val timeoutMilliseconds = valueObject.getLong("timeout")
                val currentTime = Date().time
                if (currentTime >= createTime + timeoutMilliseconds) {
                    remove(sharedPreferencesName, storeKey)
                    return null
                }
            }
            if (!valueObject.has("value")) {
                null
            } else valueObject.getString("value")
        } catch (e: JSONException) {
            null
        }
    }

    /**
     * 删除key
     *
     * @param sharedPreferencesName
     * @param key
     */
    fun remove(sharedPreferencesName: String?, key: String?) {
        val sharedPreferences =
            context!!.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    /**
     *
     * @param sharedPreferencesName
     * @param storeKey
     * @param storeValue
     */
    fun putFloat(sharedPreferencesName: String?, storeKey: String?, storeValue: Float) {
        val sharedPreferences =
            context!!.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat(storeKey, storeValue)
        editor.apply()
    }

    /**
     *
     * @param sharedPreferencesName
     * @param storeKey
     * @return
     */
    fun getFloat(sharedPreferencesName: String?, storeKey: String?): Float {
        val sharedPreferences = context!!.getSharedPreferences(
            sharedPreferencesName,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getFloat(storeKey, 0f)
    }

    fun putLong(sharedPreferencesName: String?, storeKey: String?, storeValue: Long) {
        val sharedPreferences =
            context!!.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong(storeKey, storeValue)
        editor.apply()
    }

    fun getLong(sharedPreferencesName: String?, storeKey: String?): Long {
        val sharedPreferences = context!!.getSharedPreferences(
            sharedPreferencesName,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getLong(storeKey, 0)
    }
}