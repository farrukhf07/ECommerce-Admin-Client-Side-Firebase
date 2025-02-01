package com.example.adminclientapp.views.fragment


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.adminclientapp.R
import com.example.adminclientapp.data.Item
import com.example.adminclientapp.databinding.FragmentAddItemBinding
import com.example.adminclientapp.utils.IMAGE_STORAGE
import com.example.adminclientapp.utils.ITEM_TABLE
import kotlin.random.Random

class AddItemFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentAddItemBinding?= null
    private val binding get() = _binding

//    private var itemRef: DatabaseReference?=null
//    private var storageRef: StorageReference?=null
    private var imageUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        itemRef = FirebaseDatabase.getInstance().getReference("item_tbl")
//        storageRef = FirebaseStorage.getInstance().getReference("Images")
        _binding = FragmentAddItemBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerClicks()

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

//    private fun uploadImageToFirebaseStorage(uri: Uri) {
//        val storageRef = FirebaseStorage.getInstance().reference
//        val imageRef = storageRef.child("images/${UUID.randomUUID()}")
//
//        imageRef.putFile(uri)
//            .addOnSuccessListener {
//                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
//                    val imageUrl = downloadUri.toString()
//                    saveItemToDatabase(imageUrl)
//                }
//            }
//            .addOnFailureListener {exception ->
//                Toast.makeText(context, "Failed to upload image: ${exception.message}", Toast.LENGTH_LONG).show()
//            }
//    }

    private fun saveItemToDatabase() {
        val random = Random.nextInt(1, 100)
        var item : Item
        imageUri?.let{
            IMAGE_STORAGE.child("$random").putFile(it).addOnSuccessListener { task->
                task.metadata?.reference?.downloadUrl
                    ?.addOnSuccessListener { url->
                        val imgUrl = url.toString()

                        item = Item(id = random,
                            name = binding?.etitemName?.text.toString(),
                            price = binding?.etitemPrice?.text.toString().toInt(),
                            desc = binding?.etitemDesc?.text.toString(),
                            img = imgUrl,
                            active = true)
                        ITEM_TABLE.child("$random").setValue(item)
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
        binding?.btnitemAdd?.setOnClickListener(this)
        binding?.btnBack?.setOnClickListener(this)
        binding?.imgSelect?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnitemAdd->{
                saveItemToDatabase()
            }
            R.id.btnBack->{
                findNavController().navigateUp()
            }
        }
    }

}