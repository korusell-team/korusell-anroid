package net.alienminds.ethnogram.service

import net.alienminds.ethnogram.service.auth.AuthFBService
import net.alienminds.ethnogram.service.auth.AuthService
import net.alienminds.ethnogram.service.categories.CategoriesFBService
import net.alienminds.ethnogram.service.categories.CategoriesService
import net.alienminds.ethnogram.service.cities.CitiesFBService
import net.alienminds.ethnogram.service.cities.CitiesService
import net.alienminds.ethnogram.service.users.UsersFBService
import net.alienminds.ethnogram.service.users.UsersService

object API {

    val auth: AuthService = AuthFBService()
    val users: UsersService by lazy { UsersFBService() }
    val categories: CategoriesService by lazy { CategoriesFBService() }
    val cities: CitiesService by lazy { CitiesFBService() }

}