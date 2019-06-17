package com.jdroid.java.exception

enum class CommonErrorCode(private var resourceId: Int?, private var statusCode: Int? = null) : ErrorCode {

    UNEXPECTED_ERROR(null);

    override fun getStatusCode(): String {
        return statusCode!!.toString()
    }

    override fun getTitleResId(): Int? {
        return null
    }

    override fun getDescriptionResId(): Int? {
        return resourceId
    }

    override fun newErrorCodeException(vararg errorCodeParameters: Any): ErrorCodeException {
        return ErrorCodeException(this, *errorCodeParameters)
    }

    override fun newErrorCodeException(): ErrorCodeException {
        return ErrorCodeException(this)
    }

    override fun newErrorCodeException(throwable: Throwable): ErrorCodeException {
        return ErrorCodeException(this, throwable)
    }

    override fun newErrorCodeException(message: String): ErrorCodeException {
        return ErrorCodeException(this, message)
    }

    companion object {

        fun findByStatusCode(statusCode: String): ErrorCode? {
            var errorCode: ErrorCode? = null
            for (each in values()) {
                if (each.statusCode != null && each.statusCode.toString() == statusCode) {
                    errorCode = each
                    break
                }
            }
            return errorCode
        }
    }
}
