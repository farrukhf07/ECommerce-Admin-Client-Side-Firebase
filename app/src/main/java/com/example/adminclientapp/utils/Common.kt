package com.example.adminclientapp.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

val AUTH = FirebaseAuth.getInstance()

val DATABASE = FirebaseDatabase.getInstance()

val CUSTOMER = DATABASE.getReference("cust_tbl")
val ITEM_TABLE = DATABASE.getReference("item_tbl")
val ORDER_TABLE = DATABASE.getReference("order_tbl")

// For Image
val STORAGE = FirebaseStorage.getInstance()
val IMAGE_STORAGE = STORAGE.getReference("Images")