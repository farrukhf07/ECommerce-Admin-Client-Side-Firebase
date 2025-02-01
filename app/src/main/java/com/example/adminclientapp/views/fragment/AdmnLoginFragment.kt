package com.example.adminclientapp.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.adminclientapp.R
import com.example.adminclientapp.databinding.FragmentAdmnLoginBinding
import com.google.firebase.auth.FirebaseAuth


class AdmnLoginFragment : Fragment(), View.OnClickListener {
    private var _binding : FragmentAdmnLoginBinding ?= null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAdmnLoginBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerClicks()
    }

    private fun registerClicks() {
        binding?.btnLogin?.setOnClickListener(this)
        binding?.tvCust?.setOnClickListener(this)
    }

    fun notEmptyFields():Boolean{
        return binding?.etEmail?.text.toString().isNotEmpty() && binding?.etPassword?.text.toString().isNotEmpty()
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnLogin->{
                if (binding?.etEmail?.text.toString() == "admin@gmail.com" && binding?.etPassword?.text.toString() == "admin123"){
                    findNavController().navigate(AdmnLoginFragmentDirections.actionAdmnLoginFragmentToAdmnDashboardFragment())
                    Toast.makeText(context, "Admin", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context, "Please Check Credentials", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.tv_cust->{
                findNavController().navigateUp()
            }
        }
    }

}