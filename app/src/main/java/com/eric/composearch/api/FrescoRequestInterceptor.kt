package com.eric.composearch.api

import android.net.Uri
import com.eric.basicore.utils.LogUtil
import com.eric.composearch.utils.APP_DEBUG_TAG
import okhttp3.Interceptor
import okhttp3.Response

const val FRESCO_HOST = "frodo.douban.com"
private const val FRODO_API_KEY = "0dad551ec0f84ed02907ff5c42e8ec70"

object FrescoRequestInterceptor : Interceptor {
    private const val FrescoUserAgent =
        "Rexxar-Core/0.1.3 api-client/1 com.douban.frodo/7.27.0.beta(231) Android/29 product/capricorn vendor/Xiaomi model/MI 10 pro brand/Xiaomi  rom/miui6  network/wifi  udid/83d1a9c5774cbb6ad5290121cc35e89cae25569c  platform/mobile com.douban.frodo/7.27.0.beta(231) Rexxar/1.2.151  platform/mobile 1.2.151"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val urlBuilder = request.url.newBuilder().apply {
            val timestamp = System.currentTimeMillis() / 1000
            val cleartext =
                "${request.method}&${Uri.encode(request.url.encodedPath)}&$timestamp"
            LogUtil.d(APP_DEBUG_TAG, "待签名内容=$cleartext")
            val sign = FrescoSigner.createSign(cleartext)
            LogUtil.d(APP_DEBUG_TAG, "sign=$sign")
            setEncodedQueryParameter("apikey", FRODO_API_KEY)
            setEncodedQueryParameter("_ts", timestamp.toString())
            setQueryParameter("_sig", sign)
        }

        val requestBuilder =
            request.newBuilder().url(urlBuilder.build()).addHeader("User-Agent", FrescoUserAgent)

        return chain.proceed(requestBuilder.build())
    }
}
