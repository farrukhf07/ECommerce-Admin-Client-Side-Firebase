package com.example.adminclientapp.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.adminclientapp.R
import com.example.adminclientapp.databinding.FragmentSignOutBinding
import com.example.adminclientapp.utils.AUTH
import com.google.firebase.auth.FirebaseAuth

class SignOutFragment : Fragment() , View.OnClickListener{
    private var _binding : FragmentSignOutBinding ?= null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignOutBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerClicks()
    }

    private fun registerClicks() {
        binding?.btnSignOut?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSignOut->{
                AUTH.signOut()
                if (AUTH.currentUser == null){
                    findNavController().navigate(SignOutFragmentDirections.actionSignOutFragmentToCustLogInFragment())
                    Toast.makeText(context, "User Logout", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(context, "Unable to Logout", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}