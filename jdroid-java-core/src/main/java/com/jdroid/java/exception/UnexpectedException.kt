package com.jdroid.java.exception

class UnexpectedException : ErrorCodeException {

    private var useCause = false

    constructor(message: String, cause: Throwable) : super(CommonErrorCode.UNEXPECTED_ERROR, message, cause) {
        isTrackable = true
    }

    constructor(message: String) : super(CommonErrorCode.UNEXPECTED_ERROR, message) {
        isTrackable = true
    }

    constructor(cause: Throwable) : super(CommonErrorCode.UNEXPECTED_ERROR, cause) {
        isTrackable = true
        useCause = true
    }

    override fun getThrowableToLog(): Throwable {
        return if (cause != null && useCause) cause else super.getThrowableToLog()
    }
}