package com.example.adminclientapp.views.fragment

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.navigation.fragment.findNavController
import com.example.adminclientapp.R
import com.example.adminclientapp.data.Customer
import com.example.adminclientapp.databinding.FragmentSignUpBinding
import com.example.adminclientapp.utils.AUTH
import com.example.adminclientapp.utils.CUSTOMER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern
import kotlin.random.Random

class SignUpFragment : Fragment(), View.OnClickListener {
    private var _binding :FragmentSignUpBinding ?= null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerCiclks()

//        binding?.radioGroupGender?.setOnCheckedChangeListener { group, checkedId ->
//            val selectedRadioButton = group.findViewById<RadioButton>(checkedId)
//            val gender = selectedRadioButton.text.toString()
//        }
    }


    private fun registerCiclks() {
        binding?.btnBack?.setOnClickListener(this)
        binding?.btnSubmit?.setOnClickListener(this)
    }
    fun chechValues(){
        if (binding?.etName?.text.toString().isEmpty()){
            errorToast()
        }
        if (binding?.etEmail?.text.toString().isEmpty() || isValidEmail(binding?.etEmail?.text.toString())){
            errorToast()
        }
        if (binding?.etPhoneNo?.text.toString().isEmpty()){
            errorToast()
        }
        if (binding?.etPassword?.text.toString().isEmpty()){
            errorToast()
        }
        if (binding?.etConfpass?.text.toString().isEmpty()){
            errorToast()
        }
    }

    fun isPasswordValid(password:String, confPass:String):Boolean{
        return (password.length >=3) && (password == confPass)
    }

    private fun errorToast() {
        Toast.makeText(context, "Check Fields!!", Toast.LENGTH_SHORT).show()
    }

    fun isValidEmail(email : String):Boolean{
        val pattern : Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun notEmptyFields():Boolean{
        return binding?.etName?.text.toString().isNotEmpty() && binding?.etEmail?.text.toString().isNotEmpty() && binding?.etPhoneNo?.text.toString().isNotEmpty() && binding?.etPassword?.text.toString().isNotEmpty() && binding?.etConfpass?.text.toString().isNotEmpty()
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSubmit->{
                if (notEmptyFields() && isPasswordValid(binding?.etPassword?.text.toString(), binding?.etConfpass?.text.toString())){
                    AUTH.createUserWithEmailAndPassword(binding?.etEmail?.text.toString(), binding?.etPassword?.text.toString()).addOnSuccessListener {

                        val random = Random.nextInt(1,100)
                        CUSTOMER.child("$random").setValue(
                            Customer(id = random,
                                name = binding?.etName?.text.toString(),
                                phone = binding?.etPhoneNo?.text.toString(),
                                email = binding?.etEmail?.text.toString(),
                                password = binding?.etPassword?.text.toString(),
                                confPass = binding?.etConfpass?.text.toString(),
                                gender =  binding?.radioGroupGender?.setOnCheckedChangeListener { group, checkedId ->
                                    val selectedRadioButton = group.findViewById<RadioButton>(checkedId)
                                    val gender = selectedRadioButton.text.toString()
                                }.toString()
                            )
                        ).addOnSuccessListener {
                            Toast.makeText(context, "User Added!!", Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        }.addOnFailureListener {
                            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                    }
                } else{
                    chechValues()
                }
            }
            R.id.btnBack->{
                findNavController().navigateUp()
            }
        }
    }

}