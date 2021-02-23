package com.example.kashless.DataClasses

class RecyclerDataItemClass{
    var itemTitle: String? = ""
    var listOfPrices: List<Int> ?= null

    constructor(){
        // Default constructor required for calls to Datasnapshot.getValue(Message.class)
    }
    fun toMap(): Map<String, List<Int>>{
        val result = HashMap<String, List<Int>>()
        result[itemTitle!!] = listOfPrices!!
        return result
    }
}
