package com.example.vkash.Model

class FunctionInfo {
    var name: String? = null
    var loaction: String? = null
    var title: String? = null
    var description: String? = null
    var functionID: Int? = null
    var number: String? = null
    var ownerEmail:String? = null
    var ownerUID: String? = null
    var items: HashMap<String,List<Int>>? = null
    var amountContributed: Int? = null



    constructor(){
    // Default constructor
    }
    constructor(nname: String?, loca: String?, title:String?, descri:String?, func:Int?, num: String?,email:String?,uid:String,item:HashMap<String,List<Int>>?, amount:Int){
        this.name = nname
        this.loaction = loca
        this.title = title
        this.description = descri
        this.functionID = func
        this.number = num
        this.ownerEmail = email
        this.items = item
        this.ownerUID = uid
        this.amountContributed = amount
    }
}