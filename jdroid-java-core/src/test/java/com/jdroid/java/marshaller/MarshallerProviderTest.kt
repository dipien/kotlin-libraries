package com.jdroid.java.marshaller

import com.jdroid.java.collections.Lists
import com.jdroid.java.collections.Maps
import com.jdroid.java.json.JsonMap

import org.testng.Assert
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import java.util.HashMap

class MarshallerProviderTest {

    private val marshallerProvider: MarshallerProvider = MarshallerProvider.get()

    init {
        marshallerProvider.addMarshaller(DummyClass::class.java, DummyClassMarshaller())
    }

    @DataProvider
    fun marshallDataProvider(): Iterator<Array<Any>> {
        val cases = Lists.newArrayList<Array<Any>>()
        cases.add(arrayOf(1, "1"))
        cases.add(arrayOf("1", "1"))
        cases.add(arrayOf(Lists.newArrayList(1, 2, 3), "[1,2,3]"))
        cases.add(arrayOf(Lists.newArrayList("1", "2", "3"), "[\"1\",\"2\",\"3\"]"))

        var dummyClass = DummyClass()
        dummyClass.stringProperty = "1"
        var dummyJson = "{\"stringProperty\":\"1\"}"
        cases.add(arrayOf(dummyClass, dummyJson))
        cases.add(arrayOf(Lists.newArrayList(dummyClass, dummyClass), "[$dummyJson,$dummyJson]"))

        dummyClass = DummyClass()
        dummyClass.longProperty = 2L
        dummyJson = "{\"longProperty\":2}"
        cases.add(arrayOf(dummyClass, dummyJson))
        cases.add(arrayOf(Lists.newArrayList(dummyClass, dummyClass), "[$dummyJson,$dummyJson]"))

        dummyClass = DummyClass()
        dummyClass.stringListProperty = Lists.newArrayList("3", "4")
        dummyJson = "{\"stringListProperty\":[\"3\",\"4\"]}"
        cases.add(arrayOf(Lists.newArrayList(dummyClass, dummyClass), "[$dummyJson,$dummyJson]"))

        dummyClass = DummyClass()
        dummyClass.longListProperty = Lists.newArrayList(5L, 6L)
        dummyJson = "{\"longListProperty\":[5,6]}"
        cases.add(arrayOf(dummyClass, dummyJson))
        cases.add(arrayOf(Lists.newArrayList(dummyClass, dummyClass), "[$dummyJson,$dummyJson]"))

        dummyClass = DummyClass()
        dummyClass.stringMapProperty = Maps.newLinkedHashMap()
        dummyJson = "{}"
        cases.add(arrayOf(dummyClass, dummyJson))
        cases.add(arrayOf(Lists.newArrayList(dummyClass, dummyClass), "[$dummyJson,$dummyJson]"))

        dummyClass = DummyClass()
        dummyClass.stringMapProperty = Maps.newLinkedHashMap()
        dummyClass.stringMapProperty!!["a"] = "b"
        dummyJson = "{\"stringMapProperty\":{\"a\":\"b\"}}"
        cases.add(arrayOf(dummyClass, dummyJson))
        cases.add(arrayOf(Lists.newArrayList(dummyClass, dummyClass), "[$dummyJson,$dummyJson]"))

        val map = HashMap<String, String>()
        map["a"] = "b"
        map["c"] = "d"
        dummyJson = "{\"a\":\"b\",\"c\":\"d\"}"
        cases.add(arrayOf(map, dummyJson))
        cases.add(arrayOf(Lists.newArrayList<Map<String, String>>(map, map), "[$dummyJson,$dummyJson]"))

        dummyClass = DummyClass()
        dummyClass.longMapProperty = Maps.newLinkedHashMap()
        dummyClass.longMapProperty!!["a"] = 1L
        dummyJson = "{\"longMapProperty\":{\"a\":1}}"
        cases.add(arrayOf(dummyClass, dummyJson))
        cases.add(arrayOf(Lists.newArrayList(dummyClass, dummyClass), "[$dummyJson,$dummyJson]"))

        dummyClass = DummyClass()
        dummyClass.dummyClassMapProperty = Maps.newLinkedHashMap()

        val innerDummyClass = DummyClass()
        innerDummyClass.stringProperty = "2"
        innerDummyClass.longProperty = 1L

        dummyClass.dummyClassMapProperty!!["a"] = innerDummyClass
        dummyJson = "{\"dummyClassMapProperty\":{\"a\":{\"longProperty\":1,\"stringProperty\":\"2\"}}}"
        cases.add(arrayOf(dummyClass, dummyJson))
        cases.add(arrayOf(Lists.newArrayList(dummyClass, dummyClass), "[$dummyJson,$dummyJson]"))

        cases.add(arrayOf(DummyClass(), "{}"))

        return cases.iterator()
    }

    @Test(dataProvider = "marshallDataProvider")
    fun marshall(data: Any, expectedJson: String) {
        val result = marshallerProvider.marshall(data, null, null)!!.toString()
        Assert.assertEquals(result.replace(" ", ""), expectedJson)
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

    inner class DummyClassMarshaller : Marshaller<DummyClass, JsonMap> {

        override fun marshall(value: DummyClass?, mode: MarshallerMode?, extras: Map<String, String>?): JsonMap? {
            val map = JsonMap(mode, extras)
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
