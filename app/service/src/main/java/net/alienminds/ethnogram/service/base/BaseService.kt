package net.alienminds.ethnogram.service.base

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.alienminds.ethnogram.service.base.entities.ServiceResult
import kotlin.coroutines.CoroutineContext

abstract class BaseService internal constructor(){

    protected val logTag
        get() = "Service/${javaClass.simpleName}"


    suspend fun <T>withSave(
        context: CoroutineContext = Dispatchers.IO,
        block: suspend () -> T
    ): ServiceResult<T> = withContext(context) {
        try {
            ServiceResult(
                data = block(),
            )
        } catch (e: Exception) {
            Log.e(logTag, "Service request error", e)
            ServiceResult(
                error = e
            )
        }
    }

}