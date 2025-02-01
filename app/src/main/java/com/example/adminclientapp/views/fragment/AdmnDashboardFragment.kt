package com.example.adminclientapp.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminclientapp.R
import com.example.adminclientapp.adapter.ItemAdmnAdapter
import com.example.adminclientapp.data.Item
import com.example.adminclientapp.databinding.FragmentAdmnDashboardBinding
import com.example.adminclientapp.listeners.ItemListenerA
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdmnDashboardFragment : Fragment(), View.OnClickListener, ItemListenerA {
    private var _binding : FragmentAdmnDashboardBinding ?= null
    private val binding get() = _binding

    private var admnadapter : ItemAdmnAdapter?=null
    private var itemRef : DatabaseReference?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        itemRef = FirebaseDatabase.getInstance().getReference("item_tbl")
        _binding = FragmentAdmnDashboardBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerClicks()

        admnadapter = ItemAdmnAdapter(requireContext(),this)
        binding?.rvItemMenu?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvItemMenu?.adapter = admnadapter

        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        itemRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<Item>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Item::class.java)
                    if (item?.active == true){
                        itemList.add(item)
                    }
                }
                // Update the adapter with the new list
                admnadapter?.setItems(itemList)
            }

            override fun onCancelled(error: DatabaseError) {
//                Log.e(TAG, "Database error: ${error.message}")
                Toast.makeText(context, "Failed to load data.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registerClicks() {
        binding?.floatingActionButton?.setOnClickListener(this)
        binding?.imgSetting?.setOnClickListener(this)
        binding?.btnBack?.setOnClickListener(this)
        binding?.orderHistory?.setOnClickListener(this)
    }

    override fun updateItem(item: Item) {
        findNavController().navigate(AdmnDashboardFragmentDirections.actionAdmnDashboardFragmentToUpdateItemFragment(item))
    }

    override fun deleteItem(item: Item) {
        val myRef = itemRef?.child(item.id.toString())
        myRef?.child("active")?.setValue(false)?.addOnSuccessListener {
            Toast.makeText(context, "Item Deleted Successfully!", Toast.LENGTH_SHORT).show()
        }?.addOnFailureListener {
            Toast.makeText(context, "Failed to Delete Item!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.floatingActionButton->{
                findNavController().navigate(AdmnDashboardFragmentDirections.actionAdmnDashboardFragmentToAddItemFragment())
            }
            R.id.orderHistory->{
                findNavController().navigate(AdmnDashboardFragmentDirections.actionAdmnDashboardFragmentToOrderHistoryFragment())
            }
            R.id.btnBack->{
                findNavController().navigateUp()
            }
            R.id.img_setting->{
                findNavController().navigate(AdmnDashboardFragmentDirections.actionAdmnDashboardFragmentToSignOutFragment())
            }
        }
    }

}