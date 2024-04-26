package com.specter.androidtcpcodec

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.specter.tcpcodec.DeserializeException
import com.specter.tcpcodec.SerializeException
import com.specter.tcpcodec.mapper.Mapper
import com.specter.tcpcodec.mapper.ReadCommand
import com.specter.tcpcodec.mapper.WriteCommand
import com.specter.tcpcodec.model.EncodingType
import java.io.IOException
import java.util.Locale
import java.util.TimeZone

class JsonMapper : Mapper {
    private val objectMapper by lazy {
        ObjectMapper().apply {
            setTimeZone(TimeZone.getDefault())
            setLocale(Locale.getDefault())
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            registerKotlinModule()
        }
    }

    override fun <T> read(command: ReadCommand<T>): T {
        if (command.type != EncodingType.JSON) {
            throw IllegalArgumentException(command.type.toString())
        } else {
            return try {
                objectMapper.readValue(command.payload, command.targetClass)
            } catch (e: IOException) {
                throw DeserializeException(e)
            }
        }
    }


    override fun write(command: WriteCommand): ByteArray {
        if (command.type != EncodingType.JSON) {
            throw IllegalArgumentException(command.type.toString())
        }
        if (command.payload is String) {
            return (command.payload as String).toByteArray()
        }

        try {
            return objectMapper.writeValueAsBytes(command.payload)
        } catch (e: IOException) {
            throw SerializeException(e)
        }
    }
}