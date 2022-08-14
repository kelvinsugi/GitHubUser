package com.kelvinsugiarto.gituserapp.data.model

sealed class DataResult<out T:  Any> {
    data class Success<out T : Any>(val data: T) : DataResult<T>()
    data class Unauthorized<out T : Any>(val data: T) : DataResult<T>()
    data class Error(val exception: String) : DataResult<Nothing>()

    fun handleResult(
        unathorizedDataBlock : (T) -> Unit = {},
        successDataBlock: (T) -> Unit = {},
        failureBlock: (Error) -> Unit = {},
    ) {
        when (this) {
            is Unauthorized -> unathorizedDataBlock(this.data)
            is Success -> successDataBlock(this.data)
            is Error -> failureBlock(Error(exception))
        }
    }
}