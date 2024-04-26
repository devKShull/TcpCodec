package com.specter.tcpcodec.mapper

import com.specter.tcpcodec.DeserializeException
import com.specter.tcpcodec.SerializeException
import com.specter.tcpcodec.model.EncodingType

interface Mapper {
    @Throws(DeserializeException::class)
    fun <T> read(command: ReadCommand<T>): T

    @Throws(SerializeException::class)
    fun write(command: WriteCommand): ByteArray
}

class ReadCommand<T>(
    val type: EncodingType,
    val payload: ByteArray,
    val targetClass: Class<T>
)

class WriteCommand(
    val type: EncodingType,
    val payload: Any,
)