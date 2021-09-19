package com.dipien.java.exception

enum class CommonErrorCode(private var resourceId: Int?, private var statusCode: Int? = null) : ErrorCode {

    UNEXPECTED_ERROR(null);

    override fun getStatusCode(): String? {
        return statusCode?.toString()
    }

    override fun getTitleResId(): Int? {
        return null
    }

    override fun getDescriptionResId(): Int? {
        return resourceId
    }

    override fun newErrorCodeException(vararg errorCodeParameters: Any): com.dipien.java.exception.ErrorCodeException {
        return com.dipien.java.exception.ErrorCodeException(this, *errorCodeParameters)
    }

    override fun newErrorCodeException(): com.dipien.java.exception.ErrorCodeException {
        return com.dipien.java.exception.ErrorCodeException(this)
    }

    override fun newErrorCodeException(throwable: Throwable): com.dipien.java.exception.ErrorCodeException {
        return com.dipien.java.exception.ErrorCodeException(this, throwable)
    }

    override fun newErrorCodeException(message: String): com.dipien.java.exception.ErrorCodeException {
        return com.dipien.java.exception.ErrorCodeException(this, message)
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
