package com.yan.kotlin.config

class GatewayManager {
    var gatewayMap = mutableMapOf<GatewayType,GatewayHttp>()
    init{
        var gatewayHttp = GatewayHttp()
        gatewayHttp.host = "192.168.1.58"
        gatewayHttp.port = 81
        gatewayMap += GatewayType.GATEWAY_TYPE_API to gatewayHttp

        gatewayHttp = GatewayHttp()
        gatewayHttp.host = "192.168.1.58"
        gatewayHttp.port = 82
        gatewayMap += GatewayType.GATEWAY_TYPE_IMAGE_API to gatewayHttp

        gatewayHttp = GatewayHttp()
        gatewayHttp.host = "192.168.1.58"
        gatewayHttp.port = 90
        gatewayMap += GatewayType.GATEWAY_TYPE_SOCKET_PUSH to gatewayHttp

        gatewayHttp = GatewayHttp()
        gatewayHttp.host = "192.168.1.58"
        gatewayHttp.port = 80
        gatewayMap += GatewayType.GATEWAY_TYPE_OFFICIAL to gatewayHttp
    }

    companion object{
        fun getUrl(gateType: GatewayType) :String {
            val gatewayHttp = GatewayManager().gatewayMap.get(gateType)
            return "${gatewayHttp!!.host}:${gatewayHttp.port}"
        }
    }
}

enum class GatewayType{
    /**
     * api网关
     */
    GATEWAY_TYPE_API,

    /**
     * 图片api网关
     */
    GATEWAY_TYPE_IMAGE_API,

    /**
     * 官网
     */
   GATEWAY_TYPE_OFFICIAL,

    /**
     * Websocket网关，用于消息推送
     */
    GATEWAY_TYPE_SOCKET_PUSH
}