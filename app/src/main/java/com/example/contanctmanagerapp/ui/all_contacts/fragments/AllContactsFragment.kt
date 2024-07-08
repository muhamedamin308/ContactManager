package com.example.contanctmanagerapp.ui.all_contacts.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contanctmanagerapp.databinding.FragmentAllContactsBinding
import com.example.contanctmanagerapp.ui.all_contacts.adapter.ContactsAdapter
import com.example.contanctmanagerapp.ui.viewmodel.ContactViewModel
import com.example.contanctmanagerapp.util.visibleNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllContactsFragment : Fragment() {
    private lateinit var binding: FragmentAllContactsBinding
    private val contactsViewModel by viewModels<ContactViewModel>()
    private val contactsAdapter by lazy { ContactsAdapter(this.requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        binding.addNewContactIcon.setOnClickListener {
            val action =
                AllContactsFragmentDirections.actionAllContactsFragmentToEditOrUpdateContactFragment(
                    null
                )
            findNavController().navigate(action)
        }
        contactsViewModel.allContacts.observe(viewLifecycleOwner) { contacts ->
            contactsAdapter.contacts = contacts
        }

    }

    private fun setupRecyclerView() {
        binding.contactsRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = contactsAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        visibleNavigation()
    }

}