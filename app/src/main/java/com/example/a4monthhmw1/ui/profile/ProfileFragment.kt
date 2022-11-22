package com.example.a4monthhmw1.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.a4monthhmw1.base.BaseFragment
import com.example.a4monthhmw1.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    companion object {
        const val IMAGE_REQUEST_CODE = 100
    }

    override fun setupUI() {
        binding.imgProfile.setOnClickListener {
            pickImageGallery()
        }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK){
            binding.imgProfile.setImageURI(data?.data)
        }
    }
}

