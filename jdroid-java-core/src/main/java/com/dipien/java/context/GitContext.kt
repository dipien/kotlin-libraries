package com.dipien.java.context

interface GitContext {

    fun getSha(): String?

    fun getBranch(): String?
}
