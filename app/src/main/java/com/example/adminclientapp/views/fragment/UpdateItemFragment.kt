package com.example.adminclientapp.views.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.adminclientapp.R
import com.example.adminclientapp.data.Item
import com.example.adminclientapp.databinding.FragmentUpdateItemBinding
import com.example.adminclientapp.utils.IMAGE_STORAGE
import com.example.adminclientapp.utils.ITEM_TABLE

class UpdateItemFragment : Fragment(), View.OnClickListener {
    private var _binding : FragmentUpdateItemBinding ?= null
    private val binding get() = _binding
    private val args:UpdateItemFragmentArgs by navArgs()
    private var imageUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateItemBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerClicks()
        binding?.etitemName?.setText(args.updateItem.name)
        binding?.etitemPrice?.setText(args.updateItem.price.toString())
        binding?.etitemDesc?.setText(args.updateItem.desc)
        binding?.imgView?.setImageURI(args.updateItem.img.toUri())

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            binding?.imgView?.setImageURI(it)
            if (it != null){
                imageUri = it
            }
        }

        binding?.imgSelect?.setOnClickListener{
            pickImage.launch("image/*")
        }
    }


    private fun saveItemToDatabase() {
        var item : Item

        imageUri?.let{
            IMAGE_STORAGE.child("${args.updateItem.id}").putFile(it).addOnSuccessListener { task->
                task.metadata?.reference?.downloadUrl
                    ?.addOnSuccessListener { url->
                        val imgUrl = url.toString()

                        item = Item(id = args.updateItem.id,
                            name = binding?.etitemName?.text.toString(),
                            price = binding?.etitemPrice?.text.toString().toInt(),
                            desc = binding?.etitemDesc?.text.toString(),
                            img = imgUrl)
                        ITEM_TABLE.child("${args.updateItem.id}").setValue(item)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Item added successfully", Toast.LENGTH_SHORT).show()
                                binding?.etitemName?.text?.clear()
                                binding?.etitemPrice?.text?.clear()
                                binding?.etitemDesc?.text?.clear()
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Failed to add Item", Toast.LENGTH_LONG).show()
                            }

                    }
            }
        }


    }

    private fun registerClicks() {
        binding?.btnitemUpdate?.setOnClickListener(this)
        binding?.btnBack?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnitemUpdate->{
                saveItemToDatabase()
            }
            R.id.btnBack->{
                findNavController().navigateUp()
            }
        }
    }
}