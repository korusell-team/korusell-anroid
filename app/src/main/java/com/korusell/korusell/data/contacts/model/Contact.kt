package com.korusell.korusell.data.contacts.model

import java.util.UUID

data class Contact(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val surname: String,
    var bio: String,
    var cities: List<String> = listOf(),
    var image: List<String> = listOf(),
    var categories: List<String> = listOf(),
    var subcategories: List<String> = listOf(),
    var phone: String = "",
    var instagram: String = "",
    var youtube: String = "",
    var link: String = "",
    var telegram: String = "",
    var kakao: String = "",
    var description: String = "",
    
    // places ids?
    //var places: [Place] = []
)
