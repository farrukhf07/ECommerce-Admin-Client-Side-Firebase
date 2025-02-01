package com.example.adminclientapp.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.adminclientapp.R
import com.example.adminclientapp.adapter.ItemCustAdapter
import com.example.adminclientapp.data.Item
import com.example.adminclientapp.databinding.FragmentCustDashboardBinding
import com.example.adminclientapp.listeners.ItemListernerC
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CustDashboardFragment : Fragment(), View.OnClickListener, ItemListernerC {
    private var _binding : FragmentCustDashboardBinding ?= null
    private val binding get() = _binding
    private var custadapter : ItemCustAdapter?=null

    private var itemRef : DatabaseReference?=null
    private var storageRef : StorageReference?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        itemRef = FirebaseDatabase.getInstance().getReference("item_tbl")
        storageRef = FirebaseStorage.getInstance().getReference("Images")

        _binding = FragmentCustDashboardBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerClicks()

        custadapter = ItemCustAdapter(requireContext(), this)
        binding?.rvItemMenu?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.rvItemMenu?.adapter = custadapter

        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        itemRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<Item>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Item::class.java)
                    if(item?.active == true){
                        itemList.add(item)
                    }
                }
                // Update the adapter with the new list
                custadapter?.setItems(itemList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load data.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registerClicks() {
        binding?.imgSetting?.setOnClickListener(this)
        binding?.btnBack?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_setting->{
                findNavController().navigate(CustDashboardFragmentDirections.actionCustDashboardFragmentToSignOutFragment())
            }
            R.id.btnBack->{
                findNavController().navigateUp()
            }
        }
    }

    override fun onItemClick(item: Item) {
        findNavController().navigate(CustDashboardFragmentDirections.actionCustDashboardFragmentToPlaceOrderFragment(item))
    }
}