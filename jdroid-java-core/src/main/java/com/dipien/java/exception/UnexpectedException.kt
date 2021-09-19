package com.dipien.java.exception

class UnexpectedException : ErrorCodeException {

    private var useCause = false

    constructor(message: String, cause: Throwable) : super(CommonErrorCode.UNEXPECTED_ERROR, message, cause) {
        setTrackable(true)
    }

    constructor(message: String) : super(CommonErrorCode.UNEXPECTED_ERROR, message) {
        setTrackable(true)
    }

    constructor(cause: Throwable) : super(CommonErrorCode.UNEXPECTED_ERROR, cause) {
        setTrackable(true)
        useCause = true
    }

    override fun getThrowableToLog(): Throwable {
        return if (cause != null && useCause) cause else super.getThrowableToLog()
    }
}
