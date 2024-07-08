package com.example.contanctmanagerapp.ui.all_contacts.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contanctmanagerapp.databinding.FragmentBlockedContactsBinding
import com.example.contanctmanagerapp.ui.all_contacts.adapter.ContactsAdapter
import com.example.contanctmanagerapp.ui.viewmodel.ContactViewModel
import com.example.contanctmanagerapp.util.visibleNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockedContactsFragment : Fragment() {
    private lateinit var binding: FragmentBlockedContactsBinding
    private val contactsViewModel by viewModels<ContactViewModel>()
    private val contactsAdapter by lazy { ContactsAdapter(this.requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlockedContactsBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        contactsViewModel.allBlockedContacts.observe(viewLifecycleOwner) { contacts ->
            contactsAdapter.contacts = contacts
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                contactsViewModel.deleteContact(contactsAdapter.contacts[position])
            }
        }).attachToRecyclerView(binding.contactsRecyclerview)
    }

    override fun onResume() {
        super.onResume()
        visibleNavigation()
    }

    private fun setupRecyclerView() {
        binding.contactsRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = contactsAdapter
        }
    }
}