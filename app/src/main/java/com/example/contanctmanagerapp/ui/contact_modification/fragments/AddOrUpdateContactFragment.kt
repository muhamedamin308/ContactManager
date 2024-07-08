package com.example.contanctmanagerapp.ui.contact_modification.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.contanctmanagerapp.R
import com.example.contanctmanagerapp.data.db.model.Address
import com.example.contanctmanagerapp.data.db.model.Contact
import com.example.contanctmanagerapp.data.db.model.GroupType
import com.example.contanctmanagerapp.databinding.FragmentAddUpdateContactBinding
import com.example.contanctmanagerapp.ui.viewmodel.AddressViewModel
import com.example.contanctmanagerapp.ui.viewmodel.ContactViewModel
import com.example.contanctmanagerapp.util.GroupsType
import com.example.contanctmanagerapp.util.Handlers
import com.example.contanctmanagerapp.util.invisibleNavigation
import com.example.contanctmanagerapp.util.validInput
import com.example.contanctmanagerapp.util.validPhoneNumber
import com.example.contanctmanagerapp.util.validZipCode
import com.example.contanctmanagerapp.util.validateEmail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar


@Suppress("DEPRECATION")
@AndroidEntryPoint
class AddOrUpdateContactFragment : Fragment() {
    private lateinit var binding: FragmentAddUpdateContactBinding
    private val contactViewModel by viewModels<ContactViewModel>()
    private val addressViewModel by viewModels<AddressViewModel>()
    private val args by navArgs<AddOrUpdateContactFragmentArgs>()
    private var contact: Contact? = null
    private lateinit var groupTypeSpinner: Spinner
    private var groupType: GroupType = GroupType.OTHER
    private val calendar: Calendar = Calendar.getInstance()
    private var date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contact = args.contact
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        invisibleNavigation()
        binding = FragmentAddUpdateContactBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        groupTypeSpinner = binding.spinner2
        setupSpinner()
        binding.editTextDate.setOnClickListener {
            val year: Int = calendar.get(Calendar.YEAR)
            val month: Int = calendar.get(Calendar.MONTH)
            val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(), { _: DatePicker?, year1: Int, month1: Int, dayOfMonth: Int ->
                    date = dayOfMonth.toString() + "/" + (month1 + 1) + "/" + year1
                    binding.editTextDate.setText(date)
                }, year, month, day
            )
            datePickerDialog.show()
        }
        binding.saveContactUpdates.setOnClickListener {
            saveContact()
        }
        binding.navigateBackIcon.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(), R.array.group_type, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            groupTypeSpinner.adapter = adapter
        }

        groupTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val selectedGroup = parent?.getItemAtPosition(position).toString()
                groupType = GroupsType.getGroupTypeWithString(selectedGroup) ?: GroupType.OTHER
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun saveContact() {
        binding.apply {
            val firstName = contactFirstName.text.toString()
            val lastName = contactLastName.text.toString()
            val phoneNumber = contactPhone.text.toString()
            val email = contactEmail.text.toString()
            val street = streetEdittext.text.toString()
            val city = cityEdititext.text.toString()
            val state = stateEdittext.text.toString()
            val zipcode = zipcodeEdittext.text.toString()

            if (validateInputs(
                    firstName, lastName, phoneNumber, email, street, city, state, zipcode
                )
            ) {
                val address = Address(0, street, city, state, zipcode)

                lifecycleScope.launch {
                    val existingAddressId = addressViewModel.getAddressId(address)

                    if (existingAddressId != null) {
                        addContactWithAddressId(existingAddressId)
                    } else {
                        addressViewModel.addAddress(address)
                        addressViewModel.addressId.observe(viewLifecycleOwner) { currentAddressId ->
                            if (currentAddressId > 0) {
                                addContactWithAddressId(currentAddressId.toInt())
                            } else {
                                Toast.makeText(
                                    requireContext(), "Failed to save address", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun addContactWithAddressId(addressId: Int) {
        val firstName = binding.contactFirstName.text.toString()
        val lastName = binding.contactLastName.text.toString()
        val phoneNumber = binding.contactPhone.text.toString()
        val email = binding.contactEmail.text.toString()
        val contact = Contact(
            0,
            groupType.name,
            addressId,
            "$firstName $lastName",
            phoneNumber,
            email,
            Handlers.generateRandomProfilePicture(),
            isFavorite = false,
            isBlocked = false,
            date!!
        )
        contactViewModel.addContact(contact)
        Toast.makeText(requireContext(), "Contact Saved", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }


    override fun onResume() {
        super.onResume()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onPause() {
        super.onPause()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED)
    }

    private fun validateInputs(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        email: String,
        street: String,
        city: String,
        state: String,
        zipcode: String
    ): Boolean {
        binding.apply {
            contactFirstName.error = null
            contactLastName.error = null
            contactPhone.error = null
            contactEmail.error = null
            streetEdittext.error = null
            cityEdititext.error = null
            stateEdittext.error = null
            zipcodeEdittext.error = null

            val firstNameError = validInput(firstName, "First name")
            val lastNameError = validInput(lastName, "Last name")
            val phoneNumberError = validPhoneNumber(phoneNumber)
            val emailError = validateEmail(email)
            val streetError = validInput(street, "Street")
            val cityError = validInput(city, "City")
            val stateError = validInput(state, "State")
            val zipcodeError = validZipCode(zipcode)

            val errors = listOf(
                firstNameError to contactFirstName,
                lastNameError to contactLastName,
                phoneNumberError to contactPhone,
                emailError to contactEmail,
                streetError to streetEdittext,
                cityError to cityEdititext,
                stateError to stateEdittext,
                zipcodeError to zipcodeEdittext
            )

            var isValid = true

            for ((error, layout) in errors) {
                if (error != null) {
                    layout.error = error
                    isValid = false
                }
            }

            return isValid
        }
    }

}