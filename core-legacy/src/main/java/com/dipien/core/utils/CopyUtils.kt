package com.dipien.core.utils

import com.dipien.core.exception.UnexpectedException
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

object CopyUtils {

    @Suppress("UNCHECKED_CAST")
    fun <T : Serializable> cloneSerializable(serializable: T): T {
        val cloneObject: T
        try {
            val bout = ByteArrayOutputStream()
            val oout = ObjectOutputStream(bout)
            oout.writeObject(serializable)
            val bytes = bout.toByteArray()
            oout.close()

            val bin = ByteArrayInputStream(bytes)
            val oin = ObjectInputStream(bin)
            cloneObject = oin.readObject() as
                T
            oin.close()
        } catch (e: IOException) {
            throw UnexpectedException(
                "Failed to clone " + serializable.javaClass.simpleName + " using serialization", e
            )
        } catch (e: ClassNotFoundException) {
            throw UnexpectedException("Failed to clone " + serializable.javaClass.simpleName + " using serialization", e)
        }
        return cloneObject
    }
}
