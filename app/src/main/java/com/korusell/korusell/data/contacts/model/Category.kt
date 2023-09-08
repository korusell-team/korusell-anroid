package com.korusell.korusell.data.contacts.model

data class Category(
    val name: String,
    val image: String = "",
    val subCategories: List<String>
)
