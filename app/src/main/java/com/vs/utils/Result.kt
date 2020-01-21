package com.vs.utils
/**
 * Created By Sachin
 */
sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val throwable: Throwable) : Result<Nothing>()

}