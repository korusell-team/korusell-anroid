package com.korusell.korusell.data.contacts

import com.korusell.korusell.data.contacts.model.Category
import com.korusell.korusell.data.contacts.model.Contact

interface ContactsRepositoryImpl {

    fun getFilters(): List<Category>

    fun getContacts(): List<Contact>
}