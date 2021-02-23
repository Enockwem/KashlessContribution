package com.example.kashless.Model

import okhttp3.*

class MakePayment {
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // using the api to make payment
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun RequestToMakePayment(aAmount: String,payerNumber: String,produceUUID:String):Int {
        // First, we must instantiate an OkHttpClient and create a Request object.
        var ClientID:String =  "a4c2f14f59f60c68"
        var SecretID:String =  	"a1a3cad8b13b146e"
        val client = OkHttpClient()//.newBuilder().build()
        val mediaType: MediaType? = MediaType.get("application/json")
        val body = RequestBody.create(
                mediaType,
                "{\n  \"username\": \"$ClientID\",\n  \"password\": \"$SecretID\",\n  \"action\": \"mmdeposit\",\n \"amount\": \"$aAmount\",\n \"currency\": \"UGX\",\n \"phone\":\"$payerNumber\",\n \" reference\":\"$produceUUID\" \n \"reason\":\"Contributio\" \n}"
        )
        val request = Request.Builder()
                .url("https://www.easypay.co.ug/api/")
                .post(body)
                .build()
        val response = client.newCall(request).execute()
        return response.code()
    }

    private fun sendToPayer(aAmount: String,payeeNumber: String){
        var ClientID:String =  "a4c2f14f59f60c68"
        var SecretID:String =  	"a1a3cad8b13b146e"
        val client2 = OkHttpClient().newBuilder().build()
        val mediaType: MediaType? = MediaType.parse("application/json")
        val body2 = RequestBody.create(
                mediaType,
                "{\n  \"username\": \"$ClientID\",\n  \"password\": \"$SecretID\",\n  \"action\": \"mmpayout\",\n \"amount\": \"$aAmount\",\n \"currency\": \"UGX\",\n \"phone\":\"$payeeNumber\"\n}"
        )
        val request2 = Request.Builder().url("https://www.easypay.co.ug/api/")
                .method("post",body2)
                .build()
        val response: Response = client2.newCall(request2).execute()
    }
}