package com.dipien.core.context

interface GitContext {

    fun getSha(): String?

    fun getBranch(): String?
}
