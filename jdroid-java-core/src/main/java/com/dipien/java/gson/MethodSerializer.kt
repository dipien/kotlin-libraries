package com.dipien.java.gson

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.beans.Introspector
import java.lang.reflect.Type

class MethodSerializer : JsonSerializer<Any> {

    private var gsonBuilder: GsonBuilder? = null

    constructor(gsonBuilder: GsonBuilder) {
        this.gsonBuilder = gsonBuilder
    }

    constructor() {
        gsonBuilder = GsonBuilderFactory.createGsonBuilder()
    }

    override fun serialize(src: Any, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val gson = gsonBuilder!!.create()

        val tree = gson.toJsonTree(src) as JsonObject

        try {
            val properties = Introspector.getBeanInfo(src.javaClass).propertyDescriptors
            for (property in properties) {
                if (property.readMethod.getAnnotation(ExposeMethod::class.java) != null) {
                    val result = property.readMethod.invoke(src, null as Array<Any>?)
                    tree.add(property.name, gson.toJsonTree(result))
                }
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        return tree
    }
}
