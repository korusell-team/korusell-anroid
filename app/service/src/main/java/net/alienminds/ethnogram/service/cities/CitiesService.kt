package net.alienminds.ethnogram.service.cities

import kotlinx.coroutines.flow.Flow
import net.alienminds.ethnogram.service.cities.entities.City

interface CitiesService {
    val all: Flow<List<City>>
}