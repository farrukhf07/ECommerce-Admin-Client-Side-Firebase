package com.example.adminclientapp.listeners

import com.example.adminclientapp.data.Order


interface OrderListener {
    fun onComplete(order : Order, position : Int)
    fun onCancel(order : Order, position : Int)
}