package com.dipien.core.exception

/**
 * Common interface for all the possible errors of the application
 */
interface ErrorCode {

    /**
     * @param errorCodeParameters The parameters for this [ErrorCode]'s message.
     * @return A new [ErrorCodeException] with this [ErrorCode]
     */
    fun newErrorCodeException(vararg errorCodeParameters: Any): ErrorCodeException

    fun newErrorCodeException(throwable: Throwable): ErrorCodeException

    /**
     * @return A new [ErrorCodeException]
     */
    fun newErrorCodeException(): ErrorCodeException

    /**
     * @param message The message
     * @return A new [ErrorCodeException]
     */
    fun newErrorCodeException(message: String): ErrorCodeException

    /**
     * @return The title resource id
     */
    fun getTitleResId(): Int?

    /**
     * @return The description resource id
     */
    fun getDescriptionResId(): Int?

    /**
     * @return The status code
     */
    fun getStatusCode(): String?
}
