package com.dipien.java.marshaller

import com.dipien.java.json.JsonMap
import org.junit.Assert
import org.junit.Test
import java.util.HashMap

class MarshallerProviderTest {

    private val marshallerProvider: com.dipien.java.marshaller.MarshallerProvider = com.dipien.java.marshaller.MarshallerProvider.get()

    init {
        marshallerProvider.addMarshaller(DummyClass::class.java, DummyClassMarshaller())
    }

    @Test
    fun marshallDataProvider() {
        marshall(1, "1")
        marshall("1", "1")
        marshall(listOf(1, 2, 3), "[1,2,3]")
        marshall(listOf("1", "2", "3"), "[\"1\",\"2\",\"3\"]")

        var dummyClass = DummyClass()
        dummyClass.stringProperty = "1"
        var dummyJson = "{\"stringProperty\":\"1\"}"
        marshall(dummyClass, dummyJson)
        marshall(listOf(dummyClass, dummyClass), "[$dummyJson,$dummyJson]")

        dummyClass = DummyClass()
        dummyClass.longProperty = 2L
        dummyJson = "{\"longProperty\":2}"
        marshall(dummyClass, dummyJson)
        marshall(listOf(dummyClass, dummyClass), "[$dummyJson,$dummyJson]")

        dummyClass = DummyClass()
        dummyClass.stringListProperty = listOf("3", "4")
        dummyJson = "{\"stringListProperty\":[\"3\",\"4\"]}"
        marshall(listOf(dummyClass, dummyClass), "[$dummyJson,$dummyJson]")

        dummyClass = DummyClass()
        dummyClass.longListProperty = listOf(5L, 6L)
        dummyJson = "{\"longListProperty\":[5,6]}"
        marshall(dummyClass, dummyJson)
        marshall(listOf(dummyClass, dummyClass), "[$dummyJson,$dummyJson]")

        dummyClass = DummyClass()
        dummyClass.stringMapProperty = linkedMapOf()
        dummyJson = "{}"
        marshall(dummyClass, dummyJson)
        marshall(listOf(dummyClass, dummyClass), "[$dummyJson,$dummyJson]")

        dummyClass = DummyClass()
        dummyClass.stringMapProperty = linkedMapOf()
        dummyClass.stringMapProperty!!["a"] = "b"
        dummyJson = "{\"stringMapProperty\":{\"a\":\"b\"}}"
        marshall(dummyClass, dummyJson)
        marshall(listOf(dummyClass, dummyClass), "[$dummyJson,$dummyJson]")

        val map = HashMap<String, String>()
        map["a"] = "b"
        map["c"] = "d"
        dummyJson = "{\"a\":\"b\",\"c\":\"d\"}"
        marshall(map, dummyJson)
        marshall(listOf<Map<String, String>>(map, map), "[$dummyJson,$dummyJson]")

        dummyClass = DummyClass()
        dummyClass.longMapProperty = linkedMapOf()
        dummyClass.longMapProperty!!["a"] = 1L
        dummyJson = "{\"longMapProperty\":{\"a\":1}}"
        marshall(dummyClass, dummyJson)
        marshall(listOf(dummyClass, dummyClass), "[$dummyJson,$dummyJson]")

        dummyClass = DummyClass()
        dummyClass.dummyClassMapProperty = linkedMapOf()

        val innerDummyClass = DummyClass()
        innerDummyClass.stringProperty = "2"
        innerDummyClass.longProperty = 1L

        dummyClass.dummyClassMapProperty!!["a"] = innerDummyClass
        dummyJson = "{\"dummyClassMapProperty\":{\"a\":{\"longProperty\":1,\"stringProperty\":\"2\"}}}"
        marshall(dummyClass, dummyJson)
        marshall(listOf(dummyClass, dummyClass), "[$dummyJson,$dummyJson]")

        marshall(DummyClass(), "{}")
    }

    private fun marshall(data: Any, expectedJson: String) {
        val result = marshallerProvider.marshall(data, null, null)!!.toString()
        Assert.assertEquals(expectedJson, result.replace(" ", ""))
    }

    inner class DummyClass {

        var stringProperty: String? = null
        var longProperty: Long? = null
        var stringListProperty: List<String>? = null
        var longListProperty: List<Long>? = null
        var stringMapProperty: MutableMap<String, String>? = null
        var longMapProperty: MutableMap<String, Long>? = null
        var dummyClassMapProperty: MutableMap<String, DummyClass>? = null
    }

    inner class DummyClassMarshaller : Marshaller<DummyClass, com.dipien.java.json.JsonMap> {

        override fun marshall(value: DummyClass?, mode: MarshallerMode?, extras: Map<String, String>?): com.dipien.java.json.JsonMap? {
            val map = com.dipien.java.json.JsonMap(mode, extras)
            map["stringProperty"] = value!!.stringProperty
            map["longProperty"] = value.longProperty
            map["stringListProperty"] = value.stringListProperty
            map["longListProperty"] = value.longListProperty
            map["stringMapProperty"] = value.stringMapProperty
            map["longMapProperty"] = value.longMapProperty
            map["dummyClassMapProperty"] = value.dummyClassMapProperty
            return map
        }
    }
}
