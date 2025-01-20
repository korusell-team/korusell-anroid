package net.alienminds.ethnogram.service.categories

import kotlinx.coroutines.flow.Flow
import net.alienminds.ethnogram.service.categories.entities.Category

interface CategoriesService {


    val all: Flow<List<Category>>
}