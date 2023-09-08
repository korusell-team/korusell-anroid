package com.korusell.korusell.ui.content.contacts

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.korusell.korusell.Repositories
import com.korusell.korusell.data.contacts.model.Category

@Composable
fun Contacts() = Column(
    modifier = Modifier.fillMaxSize()
){
    val vm = remember { ContactsViewModel(Repositories.contactsRepository) }
    Filters(
        list = vm.categories,
        currentCategory = vm.currentCategory,
        currentSubCategory = vm.currentSubCategory
    )
}

@Composable
private fun Filters(
    list: List<Category>,
    currentCategory: MutableState<Category?>,
    currentSubCategory: MutableState<String?>
){
    Categories(
        list = list,
        current = currentCategory
    )
    SubCategories(
        category = currentCategory.value,
        current = currentSubCategory
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Categories(
    list: List<Category>,
    current: MutableState<Category?>
){
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        list.forEach { category ->
            FilterChip(
                modifier = Modifier.padding(horizontal = 4.dp),
                selected = category == current.value,
                onClick = { current.value = when (current.value == category){
                        true -> null
                        false -> category
                    } },
                label = {
                    Text(text = category.name)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubCategories(
    category: Category?,
    current: MutableState<String?>
){
    val subCategories = category?.subCategories?: return
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        subCategories.forEach { subCategory ->
            FilterChip(
                modifier = Modifier.padding(horizontal = 4.dp),
                selected = current.value == subCategory,
                onClick = { current.value = when(subCategory == current.value){
                        true -> null
                        false -> subCategory
                    } },
                label = {
                    Text(text = subCategory)
                }
            )
        }
    }
}