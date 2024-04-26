package com.specter.tcpcodec.model

enum class CodecCode(val code: Byte) {
    NONE(0x00),
    OK(0x01),
    UNRECOGNIZED(0x02),
    SHORT_LENGTH(0x03),
    SHORT_LENGTH_FIELD(0x04),
    INVALID_HAND_SHAKE_TYPE(0x05),
    INVALID_ENCRYPTION_TYPE(0x06),
    INVALID_ENCRYPTION_MODE(0x07),
    INVALID_ENCRYPTION_PADDING(0x08),
    DECRYPT_FAIL(0x09),
    CRYPTO_CREATE_FAIL(0x0a),
    INVALID_KEY_SIZE(0x0b),
    BLANK_BODY(0x0c),
    ENCODE_FAIL(0x0d),
    LENGTH_NOT_EQUALS_READABLE(0x0e),
    MISSING_LINE_DELIMITER(0x0f),
    HANDSHAKE_TIMEOUT_NO_BEHAVIOR(0x10),
    HANDSHAKE_TIMEOUT_NO_INCOMING(0x11),
    HANDSHAKE_TIMEOUT_NO_OUTGOING(0x12),
    ;

    companion object {
        fun of(code: Byte): CodecCode {
            return CodecCode.entries.find { it.code == code } ?: NONE
        }
    }
}