package net.alienminds.ethnogram.ui.screens.session.edit_profile

import android.net.Uri
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.timeout
import net.alienminds.ethnogram.mappers.field
import net.alienminds.ethnogram.service.API
import net.alienminds.ethnogram.service.base.entities.InputField
import net.alienminds.ethnogram.service.categories.entities.Category
import net.alienminds.ethnogram.service.cities.entities.City
import net.alienminds.ethnogram.service.users.entities.User
import net.alienminds.ethnogram.utils.AppScreenModel
import kotlin.time.Duration.Companion.seconds

class EditProfileViewModel: AppScreenModel() {

    private val authProfile
        get() = API.auth.currentUser

    private val profile by API.users.me.toState(null)

    private val cityIds = mutableStateListOf<Int>()
    private val categoryIds = mutableStateListOf<Int>()

    private val addedImages = mutableStateListOf<String>()
    private val removedImages = mutableStateListOf<String>()

    private val allCategories by API.categories.all.toState(null)
    val allCities by API.cities.all.toState(null)


    var isPublic by mutableStateOf(profile?.isPublic?: false)
    var isAvailablePhone by mutableStateOf(profile?.phoneIsAvailable?: false)
    var phone by mutableStateOf(profile?.phone?: authProfile?.phoneNumber)
    var name by mutableStateOf(profile?.name)
    var surname by mutableStateOf(profile?.surname)
    var bio by mutableStateOf(profile?.bio)
    var info by mutableStateOf(profile?.info)
    val linksMap = mutableStateMapOf<User.LinkType, String?>()

    val cities by derivedStateOf { allCities?.filter { ac ->
        cityIds.any { ac.id == it }
    } }
    val categories by derivedStateOf { allCategories?.filter { ac ->
        categoryIds.any { ac.id == it }
    } }

    val images by derivedStateOf {
        addedImages
            .plus(profile?.image.orEmpty())
            .filterNot { removedImages.contains(it) }
    }


    private val isErrorAvatar by derivedStateOf { isPublic && images.isEmpty() }
    val isErrorName by derivedStateOf { isPublic && name.isNullOrEmpty() }
    val isErrorSurname by derivedStateOf { isPublic && surname.isNullOrEmpty() }
    val isErrorCategory by derivedStateOf { isPublic && categoryIds.isEmpty() }
    val isErrorPhone by derivedStateOf { isAvailablePhone && phone.isNullOrEmpty() }

    val edited by derivedStateOf {
        addedImages.isNotEmpty() ||
        removedImages.isNotEmpty() ||
        isPublic != (profile?.isPublic?: false) ||
        isAvailablePhone != (profile?.phoneIsAvailable?: false) ||
        phone != profile?.phone ||
        name != profile?.name ||
        surname != profile?.surname ||
        bio != profile?.bio ||
        info != profile?.info ||
        linksMap.any { it.value.orEmpty() != profile?.linksMap?.get(it.key).orEmpty() } ||
        (cityIds.containsAll(profile?.cities.orEmpty()) && cityIds.size == (profile?.cities?.size?: 0)).not() ||
        (categoryIds.containsAll(profile?.categories.orEmpty()) && categoryIds.size == (profile?.categories?.size?: 0)).not()
    }

    val canSave by derivedStateOf {
        edited &&
        isErrorName.not() &&
        isErrorSurname.not() &&
        isErrorCategory.not() &&
        isErrorAvatar.not() &&
        isErrorPhone.not()
    }

    val allCategoriesGrouped by derivedStateOf {
        allCategories.orEmpty()
            .groupBy { it.p_id }
            .filter { it.key != 0 }
            .mapNotNull { item ->
                allCategories?.find {
                    it.id == item.key
                }?.let {
                    it to item.value
                }
            }.toMap()
    }

    init { waitLoadingProfile() }

    @OptIn(FlowPreview::class)
    private fun waitLoadingProfile(){
        if (profile == null){
            withLoadingScope {
                val user = runCatching {
                    API.users.me.timeout(5.seconds).first { it != null }
                }.getOrNull()

                isPublic = user?.isPublic?: false
                isAvailablePhone = user?.phoneIsAvailable?: false
                phone = user?.phone?: authProfile?.phoneNumber
                name = user?.name
                surname = user?.surname
                bio = user?.bio
                info = user?.info

                cityIds.clear()
                cityIds.addAll(user?.cities.orEmpty())

                categoryIds.clear()
                categoryIds.addAll(user?.categories.orEmpty())

                user?.linksMap?.forEach { (type, value) ->
                    linksMap[type] = value
                }?: User.LinkType.entries.forEach { type ->
                    linksMap[type] = null
                }
            }
        } else{
            cityIds.clear()
            cityIds.addAll(profile?.cities.orEmpty())

            categoryIds.clear()
            categoryIds.addAll(profile?.categories.orEmpty())

            profile?.linksMap?.forEach { (type, value) ->
                linksMap[type] = value
            }?: User.LinkType.entries.forEach { type ->
                linksMap[type] = null
            }
        }
    }

    fun addImage(
        photos: List<Uri>
    ) = addedImages.addAll(0, photos.map { it.toString() })

    fun removeImage(
        photo: String? = images.firstOrNull()
    ) = photo?.let {
        when(addedImages.contains(it)){
            true -> addedImages.remove(it)
            false -> removedImages.add(it)
        }
    }

    fun selectCategory(category: Category){
        when(categoryIds.contains(category.id)){
            true -> categoryIds.remove(category.id)
            false -> categoryIds.add(category.id)
        }
    }

    fun selectCity(city: City){
        when(city.id == 0){
            true -> when(cityIds.contains(0)){
                true -> cityIds.clear()
                false -> cityIds.run {
                    clear()
                    add(city.id)
                }
            }
            false -> {
                cityIds.remove(0)
                when (cityIds.contains(city.id)) {
                    true -> cityIds.remove(city.id)
                    false -> cityIds.add(city.id)
                }
            }
        }
    }

    fun saveUser() = withLoadingScope{
        val fields = getEditedFields().run {
            when(addedImages.isNotEmpty() || removedImages.isNotEmpty()){
                true -> plus(InputField(User.Fields.IMAGE, applyImages()))
                false -> this
            }
        }
        API.users.updateUser(
            values = fields
        )
    }

    private suspend fun applyImages(): List<String> {
        val newImages = profile?.image?.toMutableList()?: mutableListOf()
        removedImages.forEach {
            API.users.removeImage(it)
            newImages.remove(it)
        }
        addedImages.mapNotNull{
            API.users.uploadImage(Uri.parse(it)).data?.toString()
        }.let { newImages.addAll(0, it) }
        removedImages.clear()
        addedImages.clear()
        return newImages
    }

    private fun getEditedFields(): List<InputField<Any>> = listOf(
        Pair(InputField(User.Fields.IS_PUBLIC, isPublic), profile?.isPublic?: false),
        Pair(InputField(User.Fields.PHONE_IS_AVAILABLE, isAvailablePhone), profile?.phoneIsAvailable?: false),
        Pair(InputField(User.Fields.PHONE, phone.orEmpty()), profile?.phone.orEmpty()),
        Pair(InputField(User.Fields.NAME, name.orEmpty()), profile?.name.orEmpty()),
        Pair(InputField(User.Fields.SURNAME, surname.orEmpty()), profile?.surname.orEmpty()),
        Pair(InputField(User.Fields.BIO, bio.orEmpty()), profile?.bio.orEmpty()),
        Pair(InputField(User.Fields.INFO, info.orEmpty()), profile?.info.orEmpty()),
        Pair(InputField(User.Fields.CATEGORIES, categoryIds), profile?.categories.orEmpty()),
        Pair(InputField(User.Fields.CITIES, cityIds), profile?.cities.orEmpty())
    ).plus(getLinksFields()).mapNotNull{ pair ->
        pair.first.takeUnless{ it.value == pair.second }
    }

    private fun getLinksFields() = linksMap.map {
        Pair(
            InputField(
                it.key.field,
                it.value.orEmpty()
            ),
            profile?.linksMap?.getOrDefault(it.key, "").orEmpty()
        )
    }
}