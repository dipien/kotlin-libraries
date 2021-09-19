package com.dipien.java.logging

import org.slf4j.helpers.MarkerIgnoringBase

/**
 * A logger wrapper that filter all log requests.
 */
class MuteLogger : MarkerIgnoringBase() {

    override fun isTraceEnabled(): Boolean {
        return false
    }

    override fun trace(msg: String) {
        // Ignore
    }

    override fun trace(format: String, param1: Any) {
        // Ignore
    }

    override fun trace(format: String, param1: Any, param2: Any) {
        // Ignore
    }

    override fun trace(format: String, argArray: Array<Any>) {
        // Ignore
    }

    override fun trace(msg: String, t: Throwable) {
        // Ignore
    }

    override fun isDebugEnabled(): Boolean {
        return false
    }

    override fun debug(msg: String) {
        // Ignore
    }

    override fun debug(format: String, arg1: Any) {
        // Ignore
    }

    override fun debug(format: String, param1: Any, param2: Any) {
        // Ignore
    }

    override fun debug(format: String, argArray: Array<Any>) {
        // Ignore
    }

    override fun debug(msg: String, t: Throwable) {
        // Ignore
    }

    override fun isInfoEnabled(): Boolean {
        return false
    }

    override fun info(msg: String) {
        // Ignore
    }

    override fun info(format: String, arg: Any) {
        // Ignore
    }

    override fun info(format: String, arg1: Any, arg2: Any) {
        // Ignore
    }

    override fun info(format: String, argArray: Array<Any>) {
        // Ignore
    }

    override fun info(msg: String, t: Throwable) {
        // Ignore
    }

    override fun isWarnEnabled(): Boolean {
        return false
    }

    override fun warn(msg: String) {
        // Ignore
    }

    override fun warn(format: String, arg: Any) {
        // Ignore
    }

    override fun warn(format: String, arg1: Any, arg2: Any) {
        // Ignore
    }

    override fun warn(format: String, argArray: Array<Any>) {
        // Ignore
    }

    override fun warn(msg: String, t: Throwable) {
        // Ignore
    }

    override fun isErrorEnabled(): Boolean {
        return false
    }

    override fun error(msg: String) {
        // Ignore
    }

    override fun error(format: String, arg: Any) {
        // Ignore
    }

    override fun error(format: String, arg1: Any, arg2: Any) {
        // Ignore
    }

    override fun error(format: String, argArray: Array<Any>) {
        // Ignore
    }

    override fun error(msg: String, t: Throwable) {
        // Ignore
    }
}
