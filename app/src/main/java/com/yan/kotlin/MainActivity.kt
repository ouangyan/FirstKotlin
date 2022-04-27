package com.yan.kotlin

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yan.kotlin.manager.SharedPreferencesManager
import com.yan.kotlin.retrofit.ApiService
import com.yan.kotlin.retrofit.ServiceCreator
import com.yyd.sdk.websocket.util.ClientDeviceInfoUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.login.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // GatewayManager需要调用SharedPreferencesManager，
        // 所以SharedPreferencesManager初始化需要放置在GatewayManager初始化前
        SharedPreferencesManager.init(applicationContext)
        setContentView(R.layout.login)

        btn_login.setOnClickListener{
            val account = et_account.text
            val pwd = et_pwd.text
            var deviceId = SharedPreferencesManager.getString("device", "deviceId")
            if (deviceId.isNullOrEmpty()) {
                deviceId = UUID.randomUUID().toString()
                SharedPreferencesManager.putString("device", "deviceId", deviceId)
            }

            var paramMap = mutableMapOf<String,Any>()
            paramMap.put("user",account)
            paramMap.put("password",pwd)
            paramMap.put("deviceId",deviceId)
            paramMap.put("clientDeviceType", ClientDeviceInfoUtil.fromStringToClientDeviceType(Build.MANUFACTURER));
            val service = ServiceCreator.create(ApiService::class.java)
            // Thread+Retrofit实现
//            Thread(object: Runnable {
//                override fun run(){
//                    service.push("/api/v2/auth/loginWithPassword",paramMap).enqueue(object: retrofit2.Callback<ResponseBody>{
//                        override fun onResponse(
//                            call: Call<ResponseBody>,
//                            response: Response<ResponseBody>) {
//                            var jsonObject = JSONObject(response.body()!!.string())
//                            var data = jsonObject.getJSONObject("data")
//                            var userId = data.getLong("userId")
//                            println("1qaz2wsx ${userId}")
//                        }
//
//                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                            t.printStackTrace()
//                        }
//                    })
//                }
//            }).start()

            // Rxjava+Retrofit实现
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                service.push2("/api/v2/auth/loginWithPassword",paramMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object: Consumer<ResponseBody> {
                        override fun accept(t: ResponseBody) {
                            var jsonObject = JSONObject(t.string())
                            var data = jsonObject.getJSONObject("data")
                            var userId = data.getLong("userId")
                            println("1qaz2wsx ${userId}")
                        }
                    })
            }
        }
    }
}
