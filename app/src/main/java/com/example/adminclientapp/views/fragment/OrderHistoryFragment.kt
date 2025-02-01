package com.example.adminclientapp.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminclientapp.R
import com.example.adminclientapp.adapter.OrderHistoryAdapter
import com.example.adminclientapp.data.Order
import com.example.adminclientapp.data.OrderStatus
import com.example.adminclientapp.databinding.FragmentOrderHistoryBinding
import com.example.adminclientapp.listeners.OrderListener
import com.example.adminclientapp.utils.ORDER_TABLE
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrderHistoryFragment : Fragment(), View.OnClickListener, OrderListener{
    private var _binding : FragmentOrderHistoryBinding ?= null
    private val binding get() = _binding
    private var orderAdapter : OrderHistoryAdapter?=null

//    private var orderRef : DatabaseReference?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        orderRef = FirebaseDatabase.getInstance().getReference("order_tbl")
        _binding = FragmentOrderHistoryBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderAdapter = OrderHistoryAdapter(requireContext(), this)
        binding?.rvOrder?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvOrder?.adapter = orderAdapter

        registerClicks()
        fetchDataFromDatabase()
    }

    private fun registerClicks() {
        binding?.btnBack?.setOnClickListener(this)
    }

    private fun fetchDataFromDatabase() {
        ORDER_TABLE.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val orderList = mutableListOf<Order>()
                for (orderSnapshot in snapshot.children){
                    val order = orderSnapshot.getValue(Order::class.java)
                    order?.let { orderList.add(it) }
                }
                orderAdapter?.setOrder(orderList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Fail to load data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnBack->{
                findNavController().navigateUp()
            }
        }
    }

    override fun onComplete(order: Order, position: Int) {
        order.order_status = OrderStatus.COMPLETE
        val status = order.order_status
        val myRef = ORDER_TABLE.child(order.order_id.toString())
        myRef.child("order_status").setValue("$status").addOnSuccessListener {
            orderAdapter?.updateOrderAtPosition(position)
            Toast.makeText(context, "Order Complete Successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(context, "Fail to Complete Order", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCancel(order: Order, position: Int) {
        order.order_status = OrderStatus.CANCEL
        val status = order.order_status
        val myRef = ORDER_TABLE.child(order.order_id.toString())
        myRef.child("order_status").setValue("$status").addOnSuccessListener {
            orderAdapter?.updateOrderAtPosition(position)
            Toast.makeText(context, "Order Cancel Successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Fail to Cancel Order", Toast.LENGTH_SHORT).show()
        }
    }

}