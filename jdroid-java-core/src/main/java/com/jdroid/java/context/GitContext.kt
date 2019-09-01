package com.jdroid.java.context

interface GitContext {

    fun getSha(): String?

    fun getBranch(): String?
}
