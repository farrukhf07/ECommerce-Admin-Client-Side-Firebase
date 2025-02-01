package com.example.adminclientapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.adminclientapp.R
import com.example.adminclientapp.data.Order
import com.example.adminclientapp.data.OrderStatus
import com.example.adminclientapp.listeners.OrderListener

class OrderHistoryAdapter(
    val context: Context,
    val listener: OrderListener
) : RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {
    var orderList = listOf<Order>()
    class ViewHolder(val views: View): RecyclerView.ViewHolder(views) {
        val card = views.findViewById<CardView>(R.id.cvOrder)
        val name = views.findViewById<TextView>(R.id.cardorderName)
        val price = views.findViewById<TextView>(R.id.cardorderPrice)
        val quantity = views.findViewById<TextView>(R.id.cardorderQuantity)
        val btnComplete = views.findViewById<Button>(R.id.btnorderComplete)
        val btnCancel = views.findViewById<Button>(R.id.btnorderCancel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.rv_order_view,parent,false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orderList[position]
        holder.name.text = order.order_name
        holder.price.text = "Rs:${order.order_price}"
        holder.quantity.text = "${order.order_quantity}"

        when(order.order_status){
            OrderStatus.CANCEL->{
                holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.card.context, R.color.red))
                holder.btnCancel.visibility = View.GONE
                holder.btnComplete.visibility = View.GONE
            }
            OrderStatus.COMPLETE->{
                holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
                holder.btnComplete.visibility = View.GONE
                holder.btnCancel.visibility = View.GONE
            }
            else -> {
                holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.card.context, R.color.orange))
            }
        }

        holder.btnComplete?.setOnClickListener {
            listener.onComplete(order, position)
        }

        holder.btnCancel?.setOnClickListener {
            listener.onCancel(order, position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setOrder(order: List<Order>) {
        orderList = order
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }

    fun updateOrderAtPosition(position: Int){
        notifyItemChanged(position)
    }
}