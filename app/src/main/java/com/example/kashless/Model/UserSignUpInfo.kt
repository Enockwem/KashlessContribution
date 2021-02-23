package com.example.vkash.Model

class UserSignUpInfo {
    var firstName: String? = null
    var lastName: String? = null
    var phoneNumber: String? = null
    var email: String? = null
    var password: String? = null
    var username: String? = null
    var ListForFunctionIDs: List<Int>? = null

    // Default constructor
    constructor(){}

    constructor(fname: String?, lname: String?, pnumber: String?, email: String?,password: String?, username: String?,ff: List<Int>?){
        this.firstName = fname
        this.lastName = lname
        this.phoneNumber = pnumber
        this.email = email
        this.password = password
        this.username = username
        this.ListForFunctionIDs = ff
    }
}