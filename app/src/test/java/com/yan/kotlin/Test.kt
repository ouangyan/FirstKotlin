package com.yan.kotlin

import com.yan.kotlin.config.GatewayManager
import com.yan.kotlin.config.GatewayType
import org.junit.Test

class Test {
    @Test
    fun test(){
        println(GatewayManager.getUrl(GatewayType.GATEWAY_TYPE_API))
    }
}