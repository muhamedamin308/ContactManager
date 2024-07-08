package com.example.contanctmanagerapp.ui.all_contacts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.contanctmanagerapp.R
import com.example.contanctmanagerapp.data.db.model.Contact
import com.example.contanctmanagerapp.databinding.ContactItemLayoutBinding
import com.example.contanctmanagerapp.ui.all_contacts.fragments.AllContactsFragmentDirections
import com.example.contanctmanagerapp.util.Handlers
import com.example.contanctmanagerapp.util.setProfileImage

class ContactsAdapter(private val context: Context) :
    RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {
    inner class ContactsViewHolder(
        private val binding: ContactItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.apply {
                contactName.text = contact.name
                contactPhoneNumber.text = contact.phone
                contactImageProfile.setProfileImage(contact.profilePictureColor)
                if (contact.isFavorite && !contact.isBlocked)
                    contactName.setTextColor(ContextCompat.getColor(context, R.color.favorite))
                else if (contact.isBlocked && !contact.isFavorite)
                    contactName.setTextColor(ContextCompat.getColor(context, R.color.blocked))
                else {
                    if (Handlers.isDark(context))
                        contactName.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.dark_font
                            )
                        )
                    else
                        contactName.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.light_font
                            )
                        )
                }
                contactLayout.setOnClickListener {
                    val action =
                        AllContactsFragmentDirections.actionAllContactsFragmentToContactDetailsFragment(
                            contact
                        )
                    itemView.findNavController().navigate(action)
                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder =
        ContactsViewHolder(
            ContactItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val currentContact = differ.currentList[position]
        holder.bind(currentContact)
    }

    var contacts: List<Contact>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }
}