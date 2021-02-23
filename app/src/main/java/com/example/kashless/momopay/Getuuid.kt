package com.example.kashless.momopay

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


class Getuuid {
    fun getuuid():  String{
        val client = OkHttpClient().newBuilder().build()
        val request: Request = Request.Builder()
            .url("https://www.uuidgenerator.net/api/version4")
            .method("GET", null)
            .build()
        val response: Response = client.newCall(request).execute()

        return response.body().toString()
    }
}