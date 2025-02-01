package com.example.adminclientapp.listeners

import com.example.adminclientapp.data.Item

interface ItemListenerA {
    fun updateItem(item: Item)

    fun deleteItem(item:Item)
}