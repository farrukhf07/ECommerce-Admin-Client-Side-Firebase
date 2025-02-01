package com.example.adminclientapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    val id : Int= 0,
    val name : String = "",
    val price : Int = 0,
    val desc : String = "",
    val img : String = "",
    var active : Boolean = true
):Parcelable
