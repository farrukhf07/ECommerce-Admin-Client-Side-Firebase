package com.example.adminclientapp.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.adminclientapp.R
import com.example.adminclientapp.databinding.FragmentCustLogInBinding
import com.example.adminclientapp.utils.AUTH

class CustLogInFragment : Fragment(), View.OnClickListener {
    private var _binding : FragmentCustLogInBinding ?= null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCustLogInBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerClicks()
    }

    private fun registerClicks() {
        binding?.btnLogin?.setOnClickListener(this)
        binding?.nonaccount?.setOnClickListener(this)
        binding?.tvAdmin?.setOnClickListener(this)
    }
    fun notEmptyFields():Boolean{
        return binding?.etEmail?.text.toString().isNotEmpty() && binding?.etPassword?.text.toString().isNotEmpty()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnLogin->{
                if (notEmptyFields()){
                    AUTH.signInWithEmailAndPassword(binding?.etEmail?.text.toString(), binding?.etPassword?.text.toString()).addOnSuccessListener {
                        Toast.makeText(context, "Successfully LogIn", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(CustLogInFragmentDirections.actionCustLogInFragmentToCustDashboardFragment())
                    }.addOnFailureListener {
                        Toast.makeText(context, "Invalid User!!", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context, "Check Email/Password", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.nonaccount->{
                findNavController().navigate(CustLogInFragmentDirections.actionCustLogInFragmentToSignUpFragment())
            }
            R.id.tv_Admin->{
                findNavController().navigate(CustLogInFragmentDirections.actionCustLogInFragmentToAdmnLoginFragment())
            }
        }

    }

}