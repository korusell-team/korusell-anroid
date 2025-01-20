package net.alienminds.ethnogram.service.users.entities

data class UpdateUser(
    val isPublic: Boolean,
    val isAvailablePhone: Boolean,
    val phone: String?,
    val name: String?,
    val surname: String?,
    val bio: String?,
    val info: String?
){
    constructor(
        user: User,
        isPublic: Boolean = user.isPublic,
        isAvailablePhone: Boolean = user.phoneIsAvailable,
        phone: String? = user.phone,
        name: String? = user.name,
        surname: String? = user.surname,
        bio: String? = user.bio,
        info: String? = user.info
    ): this(
        isPublic = isPublic,
        isAvailablePhone = isAvailablePhone,
        phone = phone,
        name = name,
        surname = surname,
        bio = bio,
        info = info
    )

    internal fun toHashMap() = hashMapOf(
        User.Fields.IS_PUBLIC.key to isPublic,
        User.Fields.PHONE_IS_AVAILABLE.key to isAvailablePhone,
        User.Fields.PHONE.key to phone,
        User.Fields.NAME.key to name,
        User.Fields.SURNAME.key to surname,
        User.Fields.BIO.key to bio,
        User.Fields.INFO to info
    )
}
