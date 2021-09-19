package com.dipien.logging

interface ExceptionLogger {
    fun logHandledException(throwable: Throwable?)
}
