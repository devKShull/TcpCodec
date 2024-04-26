package com.specter.tcpcodec.encoder

import com.specter.tcpcodec.Constants
import com.specter.tcpcodec.HandShakeCodecException
import com.specter.tcpcodec.model.HandShake
import com.specter.tcpcodec.model.HandShakeHeader
import io.github.ppzxc.crypto.Crypto
import java.nio.ByteBuffer

class HandShakeEncoder(
    private val crypto: Crypto
) {

    fun encode(msg: HandShake): ByteArray {
        val encryptedBody = makeEncryptedPayload(msg)

        return try {
            ByteBuffer.allocate(
                HandShakeHeader.LENGTH_FIELD_LENGTH +
                        HandShakeHeader.PROTOCOL_FIELDS_LENGTH +
                        encryptedBody.size +
                        Constants.LineDelimiter.LENGTH
            ).apply {
                putInt(
                    HandShakeHeader.PROTOCOL_FIELDS_LENGTH +
                            encryptedBody.size +
                            Constants.LineDelimiter.LENGTH
                )
                put(msg.header.type.code)
                put(msg.header.encryptionType.code)
                put(msg.header.encryptionMode.code)
                put(msg.header.encryptionPadding.code)
                put(encryptedBody)
                put(Constants.LineDelimiter.BYTE_ARR)
            }
        } catch (e: Exception) {
            throw HandShakeCodecException(e)
        }.array()
    }

    private fun makeEncryptedPayload(msg: HandShake): ByteArray {
        return crypto.encrypt(msg.iv + msg.aesKey)
    }
}