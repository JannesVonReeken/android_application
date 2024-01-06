package com.example.androidjannes

import com.example.androidjannes.room.Converters
import org.junit.Test
import org.junit.Assert.*

class UnitTests {

    val converter = Converters() //From Room

    @Test
    fun fromListToString(){ //Tests if the Converter can handle a list of integers and convert them into a string
        val intList = listOf(1,2,3,4,5)

        val result = converter.fromList(intList)

        assertEquals("[1,2,3,4,5]",result)
    }

    @Test
    fun fromStringToList(){ //Tests if the Converter can handle a string of integers and convert them into a list of integers
        val string = "[1,2,3,4,5]"

        val result = converter.toList(string)

        assertEquals(listOf(1,2,3,4,5), result)
    }

    @Test
    fun fromEmptyListToString(){ //Tests if the Converter can handle a list empty list from type integer
        val emptyList = emptyList<Int>()

        val result = converter.fromList(emptyList)

        assertEquals("[]", result)
    }
}