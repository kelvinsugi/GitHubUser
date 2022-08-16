package com.kelvinsugiarto.gituserapp.data.model

sealed class DataResult<out T:  Any> {
    data class Success<out T : Any>(val data: T) : DataResult<T>()
    data class Unauthorized<out T : Any>(val data: T) : DataResult<T>()
    data class Error(val exception: String) : DataResult<Nothing>()
    data class Loading(val message: String): DataResult<Nothing>()


    fun handleResult(
        unauthorizedDataBlock : (T) -> Unit = {},
        successDataBlock: (T) -> Unit = {},
        failureBlock: (Error) -> Unit = {},
        loadingBlock: (Loading) -> Unit = {}
    ) {
        when (this) {
            is Unauthorized -> unauthorizedDataBlock(this.data)
            is Success -> successDataBlock(this.data)
            is Error -> failureBlock(Error(exception))
            is Loading -> loadingBlock(Loading(message))
        }
    }
}