package com.korusell.korusell.ui.content.contacts

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korusell.korusell.data.contacts.ContactsRepositoryImpl
import com.korusell.korusell.data.contacts.model.Category
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ContactsViewModel(
    private val repo: ContactsRepositoryImpl
): ViewModel() {

    val categories = mutableStateListOf<Category>()
    val currentCategory = mutableStateOf<Category?>(null)
    val currentSubCategory = mutableStateOf<String?>(null)

    init {
        updateFilters()
        listenValidFilters()
    }

    private fun updateFilters(){
        categories.clear()
        categories.addAll(repo.getFilters())
    }

    private fun listenValidFilters(){
        snapshotFlow { categories }.onEach {
            checkValidCategory()
        }.launchIn(viewModelScope)

        snapshotFlow { currentCategory.value }.onEach {
            checkValidSubCategory()
        }.launchIn(viewModelScope)
    }

    private fun checkValidCategory(){
        currentCategory.value = categories.find {
            currentCategory.value == it
        }
    }

    private fun checkValidSubCategory(){
        currentSubCategory.value = currentCategory.value
            ?.subCategories?.find {
                it == currentSubCategory.value
            }
    }

}