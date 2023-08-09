package com.eric.composearch.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RequestInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (FRESCO_HOST == request.url.host) {
            return FrescoRequestInterceptor.intercept(chain)
        }

        return chain.proceed(request)
    }
}
