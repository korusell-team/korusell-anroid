package net.alienminds.ethnogram.service.categories.entities



class SubcategoriesNotFound(parentId: Int): Exception("Not Found Subcategories for parentId: $parentId")