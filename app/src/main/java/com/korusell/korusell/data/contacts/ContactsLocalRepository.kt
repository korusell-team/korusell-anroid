package com.korusell.korusell.data.contacts

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.korusell.korusell.data.contacts.model.Category
import com.korusell.korusell.data.contacts.model.Contact

//TODO("This is Simple Data Repository")
class ContactsLocalRepository(
    val context: Context
) : ContactsRepositoryImpl {

    override fun getFilters(): List<Category> {
        val jsonFileString = readJson("categories.json")
        val gson = Gson()
        val listCategoriesType = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson(jsonFileString, listCategoriesType)
    }

    override fun getContacts(): List<Contact> {
        val jsonFileString = readJson("contacts.json")
        val gson = Gson()
        val listContactType = object : TypeToken<List<Contact>>() {}.type
        return gson.fromJson(jsonFileString, listContactType)
    }

    private fun readJson(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}