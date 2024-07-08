package com.example.contanctmanagerapp.util

import com.example.contanctmanagerapp.data.db.model.GroupType

object GroupsType {
    fun getGroupTypeWithString(name: String): GroupType? =
        when (name) {
            "FAMILY" -> GroupType.FAMILY
            "WORK" -> GroupType.WORK
            "FRIEND" -> GroupType.FRIEND
            "OTHER" -> GroupType.OTHER
            else -> null
        }
}
