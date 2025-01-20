package net.alienminds.ethnogram.service.base.entities

data class ServiceResult<T>(
    val data: T? = null,
    val error: Exception? = null
){
    val isSuccess = error == null && data != null
}


data class ServiceDBResult<T>(
    val data: T? = null,
    val error: Exception? = null,
    val isCache: Boolean = false
){
    val isSuccess = error == null && data != null
}