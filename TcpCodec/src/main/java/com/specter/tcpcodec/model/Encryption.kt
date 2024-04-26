package com.specter.tcpcodec.model

enum class EncryptionMode(val code: Byte) {
    NONE(0x00),
    ELECTRONIC_CODE_BLOCK(0x01),
    CIPHER_BLOCK_CHAINING(0x02),
    CIPHER_FEEDBACK(0x03),
    OUTPUT_FEEDBACK(0x04),
    COUNTER(0x05);

    companion object {
        fun of(code: Byte): EncryptionMode {
            return EncryptionMode.entries.find { it.code == code } ?: NONE
        }
    }
}

enum class EncryptionPadding(val code: Byte) {
    NONE(0x00),
    PKCS5PADDING(0x01),
    PKCS7PADDING(0x02);

    companion object {
        fun of(code: Byte): EncryptionPadding {
            return EncryptionPadding.entries.find { it.code == code } ?: NONE
        }
    }
}

enum class EncryptionType(val code: Byte) {
    NONE(0x00),
    ADVANCED_ENCRYPTION_STANDARD(0x01);

    companion object {
        fun of(code: Byte): EncryptionType {
            return EncryptionType.entries.find { it.code == code } ?: NONE
        }
    }

}