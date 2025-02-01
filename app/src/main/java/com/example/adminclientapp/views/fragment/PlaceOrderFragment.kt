package com.example.adminclientapp.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.adminclientapp.R
import com.example.adminclientapp.data.Order
import com.example.adminclientapp.databinding.FragmentPlaceOrderBinding
import com.example.adminclientapp.utils.ORDER_TABLE
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.random.Random

class PlaceOrderFragment : Fragment(), View.OnClickListener {
    private var _binding : FragmentPlaceOrderBinding ?= null
    private val binding get() = _binding
    private val args : PlaceOrderFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlaceOrderBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerClicks()
        initialize()
        binding?.itemnameValue?.text = args.placeOrder.name
        binding?.itempriceValue?.text = args.placeOrder.price.toString()
    }

    private fun initialize() {
        binding?.itemquantityValue?.text=quantity.toString()
    }

    private fun registerClicks() {
        binding?.btnConfOrder?.setOnClickListener(this)
        binding?.btnBack?.setOnClickListener(this)
        binding?.btnQuantityMinus?.setOnClickListener(this)
        binding?.btnQuantityPlus?.setOnClickListener(this)
    }
    private var quantity:Int =1

    override fun onClick(v: View?) {
        var price : Int = args.placeOrder.price
        when(v?.id){
            R.id.btnQuantityMinus->{
                if (quantity > 1){
                    quantity = quantity - 1
                    price = price * quantity
                    binding?.itemquantityValue?.text = quantity.toString()
                    binding?.itempriceValue?.text = price.toString()
                }
            }
            R.id.btnQuantityPlus->{
                if (quantity < 3){
                    quantity = quantity + 1
                    price = price * quantity
                    binding?.itemquantityValue?.text = quantity.toString()
                    binding?.itempriceValue?.text = price.toString()
                }
            }
            R.id.btnConfOrder->{
                val random = Random.nextInt(1,100)
                ORDER_TABLE.child("$random").setValue(
                    Order(order_id = random,
                        order_name = binding?.itemnameValue?.text.toString(),
                        order_price = binding?.itempriceValue?.text.toString().toInt(),
                        order_quantity = binding?.itemquantityValue?.text.toString().toInt()
                        )
                ).addOnFailureListener {
                    Toast.makeText(requireContext(), "Waitaing...", Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener {
                    Toast.makeText(requireContext(), "Order Confirmed", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            }
            R.id.btnBack->{
                findNavController().navigateUp()
            }
        }
    }

}