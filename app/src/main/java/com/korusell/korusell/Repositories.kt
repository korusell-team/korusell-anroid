package com.korusell.korusell

import com.korusell.korusell.data.contacts.ContactsLocalRepository
import com.korusell.korusell.data.contacts.ContactsRepositoryImpl

object Repositories {
    val contactsRepository: ContactsRepositoryImpl = ContactsLocalRepository(KorusellApp.context!!)
}