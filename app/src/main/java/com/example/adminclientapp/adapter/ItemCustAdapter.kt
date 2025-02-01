package com.example.adminclientapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminclientapp.R
import com.example.adminclientapp.data.Item
import com.example.adminclientapp.listeners.ItemListernerC

class ItemCustAdapter(
    val context : Context,
    val listener : ItemListernerC
) : RecyclerView.Adapter<ItemCustAdapter.ViewHolder>() {
    var itemListC = listOf<Item>()
    class ViewHolder(val views : View) : RecyclerView.ViewHolder(views){
        val card = views.findViewById<CardView>(R.id.cv_itemCust)
        val name = views.findViewById<TextView>(R.id.item_name)
        val price = views.findViewById<TextView>(R.id.item_price)
        val img = views.findViewById<ImageView>(R.id.itemImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.rv_item_cust,parent,false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return itemListC.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = itemListC[position].name
        holder.price.text = "Rs ${itemListC[position].price}"
        holder.card.setOnClickListener{
            listener.onItemClick(itemListC[position])
        }

        Glide.with(context)
            .load(itemListC[position].img)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.img)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Item>) {
        itemListC= items.filter { it.active == true }.toMutableList()
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }
}