package com.example.a4monthhmw1.ui.fragment.contact

import android.R
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.widget.SimpleCursorAdapter
import androidx.core.app.ActivityCompat
import com.example.a4monthhmw1.base.BaseFragment
import com.example.a4monthhmw1.databinding.FragmentContactBinding

class ContactFragment : BaseFragment<FragmentContactBinding>(FragmentContactBinding::inflate){
    var cols = listOf(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID,
    ).toTypedArray()
    override fun setupUI() {


        if (ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), Array(1){android.Manifest.permission
                .READ_CONTACTS},111)
        }
        else
            readContact()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==111 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            readContact()
    }
    private fun readContact() {

        val from = listOf( ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,)

        val to = intArrayOf(R.id.text1, R.id.text2)

        val rs = requireContext().contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            cols,null,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

        val adapter = SimpleCursorAdapter(requireContext(), R.layout.simple_list_item_2
            ,rs, from.toTypedArray(),to,0)

       binding.listView.adapter = adapter


    }

}
