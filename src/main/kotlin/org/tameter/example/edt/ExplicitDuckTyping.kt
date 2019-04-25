package org.tameter.example.edt

import java.io.Closeable
import java.io.File

public inline fun <T : Closeable?, R> T.use(block: (T) -> R): R {
    var exception: Throwable? = null
    try {
        return block(this)
    } catch (e: Throwable) {
        exception = e
        throw e
    } finally {
        when {
            this == null -> {}
            exception == null -> close()
            else ->
                try {
                    close()
                } catch (closeException: Throwable) {
                    exception.addSuppressed(closeException)
                }
        }
    }
}

class Window {
    fun dispose() { /* ... */ }
}

inline class AutoCloseableWindow(private val window: Window): AutoCloseable {
    override fun close() {
        window.dispose()
    }
}

fun example() {
    // Works with standard JVM classes and methods
    File("dummy.txt").bufferedReader().use {
        // Process file content
    }

    AutoCloseableWindow(Window()).use {
        // Display a notification for a short time ... using continuations :-)
    }
}