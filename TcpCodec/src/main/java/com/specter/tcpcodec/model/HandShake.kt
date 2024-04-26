package com.specter.tcpcodec.model

import com.specter.tcpcodec.Constants
import com.specter.tcpcodec.Constants.LineDelimiter.LENGTH
import java.nio.ByteBuffer


enum class HandShakeType(val code: Byte) {
    NONE(0x00),
    RSA_1024(0x01),
    RSA_2048(0x02),
    RSA_4096(0x03);

    companion object {
        fun of(code: Byte): HandShakeType {
            return HandShakeType.entries.find { it.code == code } ?: NONE
        }
    }
}

class HandShakeHeader(
    val length: Int,
    val type: HandShakeType,
    val encryptionType: EncryptionType,
    val encryptionMode: EncryptionMode,
    val encryptionPadding: EncryptionPadding
) {

    companion object {
        const val LENGTH_FIELD_LENGTH = 4
        const val PROTOCOL_FIELDS_LENGTH = 4
        const val IV_PARAMETER_LENGTH = 16
        const val SYMMETRIC_KEY_FIELD_MINIMUM_LENGTH = 16
        val MINIMUM_LENGTH = LENGTH_FIELD_LENGTH + PROTOCOL_FIELDS_LENGTH +
                IV_PARAMETER_LENGTH + SYMMETRIC_KEY_FIELD_MINIMUM_LENGTH +
                Constants.LineDelimiter.LENGTH
    }
}

class HandShake(
    val header: HandShakeHeader,
    val iv: String,
    val aesKey: String
)

class HandShakeResult {
    companion object {
        const val LENGTH_FIELD_LENGTH = 4
        const val RESULT_FIELD_LENGTH = 1
        val BODY_LENGTH = RESULT_FIELD_LENGTH + Constants.LineDelimiter.LENGTH
        val LENGTH = LENGTH_FIELD_LENGTH + RESULT_FIELD_LENGTH + Constants.LineDelimiter.LENGTH
        val LENGTH_WITHOUT_LENGTH_FIELD = RESULT_FIELD_LENGTH + Constants.LineDelimiter.LENGTH

        fun of(code: CodecCode): ByteBuffer {
            return ByteBuffer.allocate(LENGTH).apply {
                putInt(LENGTH_WITHOUT_LENGTH_FIELD)
                put(code.code)
                put(Constants.LineDelimiter.BYTE_ARR)
            }
        }


    }
}