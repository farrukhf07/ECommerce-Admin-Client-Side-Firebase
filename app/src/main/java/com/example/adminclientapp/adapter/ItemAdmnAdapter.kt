package com.example.adminclientapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.adminclientapp.R
import com.example.adminclientapp.data.Item
import com.example.adminclientapp.listeners.ItemListenerA

class ItemAdmnAdapter(
    val context : Context,
    val listener : ItemListenerA
) : RecyclerView.Adapter<ItemAdmnAdapter.ViewHolder>(){
    var itemListA = listOf<Item>()
    class ViewHolder(val views : View) : RecyclerView.ViewHolder(views) {
        val card = views.findViewById<CardView>(R.id.cvItem)
        val name = views.findViewById<TextView>(R.id.carditemName)
        val price = views.findViewById<TextView>(R.id.carditemPrice)
        val update = views.findViewById<TextView>(R.id.btnitemUpdate)
        val delete = views.findViewById<TextView>(R.id.btnitemDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.rv_item_admn,parent,false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return itemListA.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = itemListA[position].name
        holder.price.text = "${itemListA[position].price}"
        holder.update.setOnClickListener{
            listener.updateItem(itemListA[position])
        }
        holder.delete.setOnClickListener{
            listener.deleteItem(itemListA[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Item>) {
        itemListA= items.filter { it.active }
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }
}