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
import net.alienminds.ethnogram.service.users.entities.User
import net.alienminds.ethnogram.utils.AppScreenModel
import kotlin.time.Duration.Companion.seconds

class EditProfileViewModel: AppScreenModel() {

    private val authProfile
        get() = API.auth.currentUser

    private val profile by API.users.me.toState(null)

    private val addedImages = mutableStateListOf<String>()
    private val removedImages = mutableStateListOf<String>()

    var isPublic by mutableStateOf(profile?.isPublic)
    var isAvailablePhone by mutableStateOf(profile?.phoneIsAvailable)
    var phone by mutableStateOf(profile?.phone?: authProfile?.phoneNumber)
    var name by mutableStateOf(profile?.name)
    var surname by mutableStateOf(profile?.surname)
    var bio by mutableStateOf(profile?.bio)
    var info by mutableStateOf(profile?.info)
    val linksMap = mutableStateMapOf<User.LinkType, String?>()


    val images by derivedStateOf {
        addedImages
            .plus(profile?.image.orEmpty())
            .filterNot { removedImages.contains(it) }
    }

    val isErrorName by derivedStateOf { (isPublic?: false) && name.isNullOrEmpty() }
    val isErrorSurname by derivedStateOf { (isPublic?: false) && surname.isNullOrEmpty() }
    val isErrorPhone by derivedStateOf { (isAvailablePhone?: false) && phone.isNullOrEmpty() }

    val edited by derivedStateOf {
        addedImages.isNotEmpty() ||
        removedImages.isNotEmpty() ||
        isPublic != profile?.isPublic ||
        isAvailablePhone != profile?.phoneIsAvailable ||
        phone != profile?.phone ||
        name != profile?.name ||
        surname != profile?.surname ||
        bio != profile?.bio ||
        info != profile?.info ||
        linksMap.any { it.value.orEmpty() != profile?.linksMap?.get(it.key).orEmpty() }
    }

    val canSave by derivedStateOf {
        fun checkPublic() =
            name.isNullOrEmpty().not() &&
            surname.isNullOrEmpty().not()

        edited &&
        (isAvailablePhone?.not()?: false || phone.isNullOrEmpty().not()) &&
        (isPublic?.not()?: false || checkPublic())
    }

    init { waitLoadingProfile() }

    @OptIn(FlowPreview::class)
    private fun waitLoadingProfile(){
        if (profile == null){
            withLoadingScope {
                val user = runCatching {
                    API.users.me.timeout(5.seconds).first { it != null }
                }.getOrNull()
                isPublic = user?.isPublic
                isAvailablePhone = user?.phoneIsAvailable
                phone = user?.phone?: authProfile?.phoneNumber
                name = user?.name
                surname = user?.surname
                bio = user?.bio
                info = user?.info
                user?.linksMap?.forEach { (type, value) ->
                    linksMap[type] = value
                }?: User.LinkType.entries.forEach { type ->
                    linksMap[type] = null
                }
            }
        } else{
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

    fun saveUser() = withLoadingScope{
//        removedImages.forEach {
//            API.users.removeImage(it)
//        }
//        val images = addedImages.map {
//            API.users.uploadImage(Uri.parse(it)).data.toString()
//        }

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

    private fun getEditedFields(): List<InputField<Any>> = listOf<Pair<InputField<Any>, Any>>(
        Pair(InputField(User.Fields.IS_PUBLIC, isPublic?: false), profile?.isPublic?: false),
        Pair(InputField(User.Fields.PHONE_IS_AVAILABLE, isAvailablePhone?: false), profile?.phoneIsAvailable?: false),
        Pair(InputField(User.Fields.PHONE, phone.orEmpty()), profile?.phone.orEmpty()),
        Pair(InputField(User.Fields.NAME, name.orEmpty()), profile?.name.orEmpty()),
        Pair(InputField(User.Fields.SURNAME, surname.orEmpty()), profile?.surname.orEmpty()),
        Pair(InputField(User.Fields.BIO, bio.orEmpty()), profile?.bio.orEmpty()),
        Pair(InputField(User.Fields.INFO, info.orEmpty()), profile?.info.orEmpty()),
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