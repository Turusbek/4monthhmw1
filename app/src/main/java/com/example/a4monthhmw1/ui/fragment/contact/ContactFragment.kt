package com.example.a4monthhmw1.ui.fragment.contact

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import com.example.a4monthhmw1.base.BaseFragment
import com.example.a4monthhmw1.databinding.FragmentContactBinding
import com.example.a4monthhmw1.model.ContactModel

class ContactFragment : BaseFragment<FragmentContactBinding>(FragmentContactBinding::inflate),
    ContactAdapter.ShareListener {
    private lateinit var adapter: ContactAdapter

    override fun setupUI() {
        adapter = ContactAdapter(this)
        binding.rvContact.adapter = adapter

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_CONTACTS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), Array(1) {
                android.Manifest.permission
                    .READ_CONTACTS
            }, 111)
        } else
            readContact()
    }

    @SuppressLint("Range")
    private fun readContact() {
        val list = arrayListOf<ContactModel>()

        val contentResolver = requireActivity().contentResolver
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
        if (cursor?.count!! > 0) {
            while (cursor.moveToNext())

                if (Integer.parseInt
                        (
                        cursor.getString
                            (
                            cursor.getColumnIndex
                                (ContactsContract.Contacts.HAS_PHONE_NUMBER)
                        )
                    ) > 0
                ) {

                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                    val phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        arrayOf(id), null
                    )
                    if (phoneCursor?.moveToNext()!!) {
                        val phoneNumber = phoneCursor.getString(
                            phoneCursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                        )
                        phoneCursor.close()
                        list.add(ContactModel(name, phoneNumber))
                    }
                    phoneCursor.close()

                }
        }
        cursor.close()
        adapter.setList(list)

    }

    override fun share(number: String, shareSwitch: Boolean) {
        if (shareSwitch) {
            AlertDialog.Builder(requireContext())
                .setTitle("Перейти на номер $number?")
                .setNegativeButton("Нет", null)
                .setPositiveButton("Да") { _: DialogInterface?, _: Int ->
                    val url = "https://api.whatsapp.com/send?phone=$number"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    requireActivity().startActivity(intent)
                }.show()
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle("Перейти на номер $number?")
                .setNegativeButton("Нет", null)
                .setPositiveButton("Да") { _: DialogInterface?, _: Int ->
                    val intent =
                        Intent(
                            Intent.ACTION_VIEW, Uri.fromParts("tel", number, number)
                        )
                    requireActivity().startActivity(intent)
                }.show()
        }
    }
}

