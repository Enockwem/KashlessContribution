package com.example.kashless.Model

class UnderFID {
    var name: String? = null
    var loaction: String? = null
    var title: String? = null
    var description: String? = null
    var number: String? = null
    var items: HashMap<String,List<Int>>? = null



    constructor(){
        // Default constructor
    }
    constructor(nname: String?, loca: String?, title:String?, descri:String?, /*func:Int?,*/ num: String?, item:HashMap<String,List<Int>>?){
        this.name = nname
        this.loaction = loca
        this.title = title
        this.description = descri
        //this.functionID = func
        this.number = num
        this.items = item
    }
}