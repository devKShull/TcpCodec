package com.specter.tcpcodec.model

enum class EncodingType(val code: Byte) {
    NULL(0x00),
    PROTOBUF(0x01),
    JSON(0x02),
    BSON(0x03),
    JAVA_SERIALIZE(0x04);

    companion object {
        fun of(code: Byte): EncodingType {
            return EncodingType.entries.find { it.code == code } ?: NULL
        }
    }
}