package com.yan.kotlin.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

object ServiceCreator {
    // api
    private val retrofitApi = Retrofit.Builder()
        .baseUrl("http://192.168.1.58:81/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // 适配器  支持Rxjava链式调用
        .build()

    // 远程环境 api
//    private val retrofitApi = Retrofit.Builder()
//        .baseUrl("http://106.75.135.173:44791")
//        .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // 适配器  支持Rxjava链式调用
//        .build()

    // 图片
    private val retrofitImageApi = Retrofit.Builder()
        .baseUrl("http://192.168.1.58:82/")
        .build()

    // socket推送
    private val retrofitSocketPush = Retrofit.Builder()
        .baseUrl("http://192.168.1.58:90/")
        .build()

    // 官网
    private val retrofitOfficial = Retrofit.Builder()
        .baseUrl("http://192.168.1.58:80/")
        .build()

    /**
     * 请求Api
     */
    fun <T> create(serviceClass: Class<T>) : T = retrofitApi.create(serviceClass)

    /**
     * 请求imageApi
     */
    fun <T> createImageApi(serviceClass: Class<T>) : T = retrofitImageApi.create(serviceClass)

    /**
     * 请求socket推送
     */
    fun <T> createSocketApi(serviceClass: Class<T>) : T = retrofitSocketPush.create(serviceClass)

    /**
     * 请求官网
     */
    fun <T> createOfficialApi(serviceClass: Class<T>) : T = retrofitOfficial.create(serviceClass)
}