package com.example.contanctmanagerapp.util

import android.util.Patterns

fun validateInputs(
    input: String,
    fieldName: String,
    additionalValidations: ((String) -> String?)? = null
): String? {
    if (input.isBlank()) return "$fieldName cannot be empty"
    return additionalValidations?.invoke(input)
}

fun validateEmail(email: String): String? =
    validateInputs(email, "Email") {
        if (!Patterns.EMAIL_ADDRESS.matcher(it).matches()) "Invalid email address" else null
    }

fun validPhoneNumber(phoneNumber: String): String? =
    validateInputs(phoneNumber, "Phone number") {
        if (it.length != 11) "Phone number must be 11 digits" else null
    }

fun validZipCode(zipCode: String): String? =
    validateInputs(zipCode, "Zip code") {
        if (it.length != 5) "Zip code must be 5 digits" else null
    }

fun validInput(input: String, fieldName: String): String? = validateInputs(input, fieldName)