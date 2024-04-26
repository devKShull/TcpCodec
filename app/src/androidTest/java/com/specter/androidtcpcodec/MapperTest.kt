package com.specter.androidtcpcodec

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.specter.tcpcodec.mapper.Mapper
import com.specter.tcpcodec.mapper.ReadCommand
import com.specter.tcpcodec.mapper.WriteCommand
import com.specter.tcpcodec.model.EncodingType
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MapperTest {
    private val dtoString =
        "{\"id\":289923104,\"text\":\"Hi this is amanda why are you there\",\"boolValue\":true,\"intValue\":13}"
    private val dto = MappingExample()
    private lateinit var mapper: Mapper

    @Before
    fun setUp() {
        mapper = JsonMapper()
    }

    @Test
    fun serializeDto() {
        val encoded = mapper.write(WriteCommand(EncodingType.JSON, dto))
        println("before encoded >> $dto")
        println("Encoded string >> $encoded")

        Assert.assertEquals(dtoString.toByteArray(), encoded)
    }

    @Test
    fun deserializeDto() {
        val decoded =
            mapper.read(ReadCommand(EncodingType.JSON, dtoString.toByteArray(), dto::class.java))
        println("decoded $decoded")

        Assert.assertEquals(dto, decoded)
    }
}


data class MappingExample(
    val id: Long = 289923104,
    val text: String = "Hi this is amanda why are you there",
    val boolValue: Boolean = true,
    val intValue: Int = 13
)