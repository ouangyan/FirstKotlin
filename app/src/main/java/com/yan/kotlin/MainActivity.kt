package com.yan.kotlin

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yan.kotlin.retrofit.ApiService
import com.yan.kotlin.retrofit.ServiceCreator
import com.yyd.common.preferences.SharedPreferencesManager
import com.yyd.sdk.websocket.util.ClientDeviceInfoUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login.*
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        btn_login.setOnClickListener{
            var headerMap = mutableMapOf<String,String>()

            val account = et_account.text
            val pwd = et_pwd.text
            var deviceId = SharedPreferencesManager.getInstance().getString("device", "deviceId")
            if (deviceId.isBlank()) {
                deviceId = UUID.randomUUID().toString()
                SharedPreferencesManager.getInstance().putString("device", "deviceId", deviceId)
            }

            var paramMap = mutableMapOf<String,Any>()
            paramMap.put("user",account)
            paramMap.put("password",pwd)
            paramMap.put("deviceId",deviceId)
            paramMap.put("clientDeviceType", ClientDeviceInfoUtil.fromStringToClientDeviceType(Build.MANUFACTURER));
            val service = ServiceCreator.create(ApiService::class.java)
            var responseBody = service.push(headerMap,"/api/v2/auth/loginWithPassword",paramMap)
        }
    }
}