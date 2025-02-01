package com.example.adminclientapp.data

data class Customer(
    val id : Int = 0,
    val name : String,
    val phone : String,
    val email : String,
    val password : String,
    val confPass : String,
    val gender : String
)
