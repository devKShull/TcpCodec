package com.specter.tcpcodec.model

import com.specter.tcpcodec.Constants

class Header(
    val length: Int,
    val id: Long,
    val type: Byte,
    val status: Byte,
    val encoding: Byte,
    val reserved: Byte,
) {

    override fun toString(): String {
        return "HEADER{length: $length, id: $id, type: $type, status: $status, encoding: $encoding, reserved: $reserved}"
    }

    companion object {
        const val LENGTH_FIELD_LENGTH = 4
        const val ID_FIELD_LENGTH = 8
        const val PROTOCOL_FIELDS_LENGTH = 4
        val BODY_LENGTH = ID_FIELD_LENGTH + PROTOCOL_FIELDS_LENGTH + Constants.LineDelimiter.LENGTH
        val MINIMUM_LENGTH = LENGTH_FIELD_LENGTH + BODY_LENGTH
        const val ENCRYPTED_EMPTY_FULL_LENGTH = 30
        const val ENCRYPTED_EMPTY_BODY_LENGTH = ENCRYPTED_EMPTY_FULL_LENGTH - LENGTH_FIELD_LENGTH
    }
}