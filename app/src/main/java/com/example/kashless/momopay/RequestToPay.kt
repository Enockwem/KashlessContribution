package com.example.kashless.momopay

import okhttp3.*
import java.io.IOException
import java.util.*

class RequestToPay {
    fun requestToPay(amount: String, payerNumber: String): Boolean{
        var checkV: Boolean = false
        // First, we must instantiate an OkHttpClient and create a Request object.
//        var ClientID:String =  "a4c2f14f59f60c68"
//        var SecretID:String =  	"a1a3cad8b13b146e"
        val produceUUID = Calendar.MILLISECOND.toString()
        var ClientID = "e8e4ddebe3518856"
        var SecretID = "70c7fe202fabdea0"
        val client = OkHttpClient()//.newBuilder().build()
        val mediaType: MediaType? = MediaType.get("application/json")
        val body = RequestBody.create(
                mediaType,
                "{\n  \"username\": \"$ClientID\",\n  \"password\": \"$SecretID\",\n  \"action\": \"mmdeposit\",\n \"amount\": \"$amount\",\n \"currency\": \"UGX\",\n \"phone\":\"$payerNumber\",\n \" reference\":\"$produceUUID\" \n \"reason\":\"Contributio\" \n}"
        )
        val request = Request.Builder()
                .method("POST",body)
                .url("https://www.easypay.co.ug/api/")
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle this
                checkV
            }

            override fun onResponse(call: Call, response: Response) {
                // Handle this
                checkV = true
            }
        })
        return checkV
    }
//    Toast.makeText(this,"Operation successful",Toast.LENGTH_SHORT).show()
//    //if(code == 1){
//    // Do something when money is up on the system
//    // notify the application in the field of the item
//    val rootRef = FirebaseDatabase.getInstance().reference
//    val uidRef = rootRef.child("function")
//            .child(intent.getStringExtra("CheckValue")!!)//.child("items")
//    val valueEventListener = object : ValueEventListener {
//        override fun onDataChange(snapshot: DataSnapshot) {
//            if (snapshot.exists()) {
//                uidRef.child("items/1").setValue("covered")
//            }
//        }
//
//        override fun onCancelled(error: DatabaseError) {
//            Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
//        }
//    }
//    uidRef.addValueEventListener(valueEventListener)
//    uidRef.addListenerForSingleValueEvent(valueEventListener)
//    //}
}


// "X-Reference-Id" - "05a99827-f49a-4376-bdb7-0a84f092f590"
// what i had in the authentication above
// eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6ImU5Y2YxNmFlLTNhODctNGFlOC05MWM2LWU3MDA3ZjEzN2ZmNiIsImV4cGlyZXMiOiIyMDIxLTAyLTA4VDE0OjU0OjM5LjI0NSIsInNlc3Npb25JZCI6ImE5NThiMGYyLTVhNTktNDg1OC05MDIxLWU2ZWE2ZDNmMDE4OSJ9.AJqVil2DSs6KysA7bNw5A2gWYjxB0pWbhuHUETEDKS7CoJmNYTiEJK8hon-7fRd6TzOvkqCv1vDpTy_Mq3i2mKMLKt8UobKLhBXRd1tsRhrDwIXYOBn6EujVu2wpfWF7IS0wSWEp6aOBhbn-BHsmix_3rS-et9lg_fHTa0iNyG1h5XYvqf3ESIa0ktCrMcVcchGdbrzBLYOBwnqRnuGZjY0VtX7VAfPogRgux08MahLk37HJfHdSpQB0n0hl6dzS83YuQE9N4Lh6SWc7SPH-V5SrI3K8IcAA5hGE5Mczy94K3iF1OLeG45yrkgreP3e90e0PUGAWMimjm5k5Wf2bBA"