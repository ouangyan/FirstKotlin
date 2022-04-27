package com.yan.kotlin.retrofit

import io.reactivex.rxjava3.core.Flowable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST
    @FormUrlEncoded
    fun push(@Url url: String,@FieldMap paramMap:@JvmSuppressWildcards Map<String,Any>) : Call<ResponseBody>

    @POST
    @FormUrlEncoded
    fun push2(@Url url: String,@FieldMap paramMap:@JvmSuppressWildcards Map<String,Any>) : Flowable<ResponseBody>

    @POST
    @FormUrlEncoded
    fun push(@HeaderMap headMap: Map<String,String>?, @Url url: String,@FieldMap paramMap:@JvmSuppressWildcards Map<String,Any>) : Call<ResponseBody>
}