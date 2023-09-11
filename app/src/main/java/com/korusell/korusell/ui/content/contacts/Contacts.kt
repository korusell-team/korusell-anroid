package com.korusell.korusell.ui.content.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.korusell.korusell.Repositories
import com.korusell.korusell.data.contacts.model.Category
import com.korusell.korusell.data.contacts.model.Contact
import com.korusell.korusell.ui.extentions.NetImage
import com.korusell.korusell.ui.theme.KORUSELLTheme

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
    ContactsList(
        contacts = vm.contacts,
        currentCategory = vm.currentCategory.value,
        currentSubCategory = vm.currentSubCategory.value
    )
}

@Composable
private fun ContactsList(
    contacts: List<Contact>,
    currentCategory: Category?,
    currentSubCategory: String?
){
    val filteredContacts = contacts.filter { contact ->
        if(currentCategory == null) true
        else {
            val hasCategory = contact.categories.find {
                currentCategory.name == it
            } != null

            val isNullSubCategory = currentSubCategory == null
            val hasSubCategory = contact.subcategories.find {
                currentSubCategory == it
            } != null

            hasCategory && (isNullSubCategory || hasSubCategory)
        }
    }
    LazyColumn {
        items(filteredContacts){ contact ->
            ItemContact(
                contact = contact,
                height = 64.dp
            )
        }
    }
}

@Composable
private fun ItemContact(
    contact: Contact,
    height: Dp
) = Row(
    modifier = Modifier
        .height(height)
        .fillMaxWidth()
        .padding(
            vertical = 2.dp,
            horizontal = 8.dp
        ),
    verticalAlignment = Alignment.CenterVertically
){

    NetImage(
        modifier = Modifier
            .clip(CircleShape)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
            )
            .size(height - 8.dp),
        url = contact.image.firstOrNull() 
    ) {
        val placeText = "${contact.name.first()}${contact.surname.first()}"
        Text(text = placeText)
    }

    Column {
        Text(
            text = "${contact.surname} ${contact.name}"
        )
        var allTags = ""
        contact.categories.forEach { allTags += " • $it" }
        contact.subcategories.forEach { allTags += " • $it"}
        allTags = allTags.trim('•', ' ')
        Text(
            text = allTags,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
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

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewContacts(){
    KORUSELLTheme {
        Contacts()
    }
}