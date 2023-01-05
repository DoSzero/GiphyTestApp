package com.dk.giphytestapp.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class GiphyAuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(addKeyQuery(chain.request()))
    }

    private fun addKeyQuery(request: Request): Request {
        val updatedUrl = request.url
            .newBuilder()
            .addQueryParameter("api_key","wgVi4F5l9VOvA99Lk4xPM7RaUAW8Kahj")
            .build()
        return request.newBuilder().url(updatedUrl).build()
    }

}
