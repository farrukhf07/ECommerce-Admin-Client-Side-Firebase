package com.example.adminclientapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    val order_id : Int = 0,
    val order_name : String = "",
    val order_price : Int = 0,
    val order_quantity : Int = 0,
    var order_status : OrderStatus = OrderStatus.PENDING
):Parcelable
