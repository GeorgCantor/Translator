package com.example.translator.model

import android.content.Context
import com.example.translator.util.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class OfflineCacheInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!context.isNetworkAvailable()) {
            request = request.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, only-if-cached, max-stale=" + 2419200)
                .build()
        }

        return chain.proceed(request)
    }
}
