package com.spacex.shared.extensions


fun tryCatch(block: () -> Unit) {
    try {
        block()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

inline fun Any?.ifNotNull(block: () -> Unit): Boolean {
    if (this != null) {
        block()
        return true
    }
    return false
}

inline fun <T : Any> isNotNull(value: T?, block: () -> Any): Boolean {
    if (value != null) {
        block()
        return true
    }
    return false
}