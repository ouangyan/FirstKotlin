package com.yan.kotlin.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST
    @FormUrlEncoded
    fun push(@HeaderMap headMap: Map<String,String>, @Url url: String,@FieldMap paramMap: Map<String,Any>) : Call<ResponseBody>
}