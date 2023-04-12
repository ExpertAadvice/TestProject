package com.roomdb.testproject.ui.fragments.contentprovider

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.roomdb.testproject.R
import com.roomdb.testproject.databinding.FragmentContentProviderBinding
import com.roomdb.testproject.utils.toast


class ContentProviderFragment : Fragment() {

    private lateinit var binding: FragmentContentProviderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_content_provider, container, false)
        binding = FragmentContentProviderBinding.bind(view)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.contactList.setOnClickListener {
            binding.showContactTv.text = getContactList().let {
                var string = ""
                it.forEach { contact ->
                    string += "${contact.keys} - "
                    string += "${contact.values}\n"
                }
                string = string.replace("[", "")
                string = string.replace("]", "")
                string
            }
        }

        binding.showContactTv.setOnClickListener {
            binding.showContactTv.text = ""
        }

    }

    @SuppressLint("Range")
    private fun getContactList(): MutableSet<MutableMap<String, String>> {

        val contactSet = mutableSetOf<MutableMap<String, String>>()
            if (checkContactPermission()) {

                val contentResolver = requireActivity().applicationContext.contentResolver

                val cursor = contentResolver.query(
                    Phone.CONTENT_URI,
                    null,
                    null,
                    null,
                    Phone.DISPLAY_NAME + " ASC"
                )
                while (cursor!!.moveToNext()) {
                    val name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME))
                    val phoneNumber =
                        cursor.getString(cursor.getColumnIndex(Phone.NUMBER))

                    val map = mutableMapOf(name to phoneNumber)
                    contactSet.add(map)
                }
                cursor.close()
            } else {
                requestContactPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        return contactSet
    }

    private fun checkContactPermission(): Boolean {
        val notification = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_CONTACTS
        )
        return notification == PackageManager.PERMISSION_GRANTED
    }

    private val requestContactPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getContactList()
        } else {
            requireContext().toast("READ Contact Permission denied")
        }
    }

}