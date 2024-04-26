package com.specter.tcpcodec.encoder

import com.specter.tcpcodec.Constants
import com.specter.tcpcodec.OutboundCodecException
import com.specter.tcpcodec.mapper.Mapper
import com.specter.tcpcodec.mapper.WriteCommand
import com.specter.tcpcodec.model.EncodingType
import com.specter.tcpcodec.model.Header
import com.specter.tcpcodec.model.OutboundProtocol
import io.github.ppzxc.crypto.Crypto
import java.nio.ByteBuffer

class OutBoundProtocolEncoder(
    private val crypto: Crypto,
    private val mapper: Mapper
) {

    fun encode(msg: OutboundProtocol): ByteArray {
        val encryptedBody = getEncryptedPayload(msg)

        return try {
            ByteBuffer.allocate(
                Header.LENGTH_FIELD_LENGTH +
                        encryptedBody.size +
                        Constants.LineDelimiter.LENGTH
            ).apply {
                putInt(encryptedBody.size + Constants.LineDelimiter.LENGTH)
                put(encryptedBody)
                put(Constants.LineDelimiter.BYTE_ARR)
            }
        } catch (exception: Exception) {
            throw OutboundCodecException()
        }.array()
    }

    private fun getEncryptedPayload(msg: OutboundProtocol): ByteArray {
        val encodedBody = getMappedBody(msg)
        val buffer = ByteBuffer.allocate(
            Header.ID_FIELD_LENGTH +
                    Header.PROTOCOL_FIELDS_LENGTH +
                    encodedBody.size
        ).apply {
            putLong(msg.header.id)
            put(msg.header.type)
            put(msg.header.status)
            put(msg.header.encoding)
            put(msg.header.reserved)
            put(encodedBody)
        }

        return crypto.encrypt(buffer.array())
    }

    private fun getMappedBody(msg: OutboundProtocol): ByteArray {
        return msg.body?.let {
            mapper.write(
                WriteCommand(
                    EncodingType.of(msg.header.encoding),
                    msg.body
                )
            )
        } ?: byteArrayOf()
    }
}