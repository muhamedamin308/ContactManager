package com.example.contanctmanagerapp.ui.contact_details.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.contanctmanagerapp.data.db.model.Contact
import com.example.contanctmanagerapp.databinding.FragmentContactDetailsBinding
import com.example.contanctmanagerapp.ui.viewmodel.AddressViewModel
import com.example.contanctmanagerapp.ui.viewmodel.ContactViewModel
import com.example.contanctmanagerapp.util.invisibleNavigation
import com.example.contanctmanagerapp.util.setProfileImage
import com.example.contanctmanagerapp.util.styleState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDetailsFragment : Fragment() {
    private lateinit var binding: FragmentContactDetailsBinding
    private val contactViewModel by viewModels<ContactViewModel>()
    private val addressViewModel by viewModels<AddressViewModel>()
    private val args by navArgs<ContactDetailsFragmentArgs>()
    private var contactId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactId = args.contact.id // Assume you pass only contact ID through arguments
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        invisibleNavigation()
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigateBackIcon.setOnClickListener { findNavController().navigateUp() }
        // Observe the contact details
        contactViewModel.getContactById(contactId).observe(viewLifecycleOwner) { contact ->
            contact?.let {
                updateUI(it)
                binding.favouriteContactState.setOnClickListener {
                    contactViewModel.toggleFavoriteContact(contact)
                }
                binding.blockedContactState.setOnClickListener {
                    contactViewModel.toggleBlockContact(contact)
                }
            }
        }

        // Observe the favorite state changes
        contactViewModel.favoriteStateUpdated.observe(viewLifecycleOwner) { updatedContact ->
            if (updatedContact.id == contactId)
                updateUI(updatedContact)
        }
        contactViewModel.blockStateUpdated.observe(viewLifecycleOwner) { updatedContact ->
            if (updatedContact.id == contactId)
                updateUI(updatedContact)
        }
    }

    private fun updateUI(contact: Contact) {
        binding.favouriteContactState.styleState(contact.isFavorite, requireContext())
        binding.blockedContactState.styleState(contact.isBlocked, requireContext())
        binding.contactProfile.setProfileImage(contact.profilePictureColor)
        binding.contactName.text = contact.name
        binding.contactPhone.text = contact.phone
        binding.contactEmail.text = contact.email
        binding.birthdateTextview.text = contact.birthdate
        binding.grouptypeTextview.text = contact.groupType

        addressViewModel.getAddressById(contact).observe(viewLifecycleOwner) { address ->
            address?.let {
                binding.streetTextview.text = it.street
                binding.cityTextview.text = it.city
                binding.stateTextview.text = it.state
                binding.zipcodeTextview.text = it.zipCode
            }
        }
    }
}
