package com.specter.tcpcodec

import com.specter.tcpcodec.model.CodecCode


open class CodecException(message: String, val code: CodecCode) : Exception(message)

class BlankBodyCodecException(message: String) : CodecException(message, CodecCode.BLANK_BODY)
class DecryptCodecException(rejectedValue: String, code: CodecCode) :
    CodecException("rejected: $rejectedValue", code)

class DeserializeException(message: Throwable) : Exception("deserialize failed", message)
class HandShakeCodecException(e: Throwable) : Exception("handShake encode failed", e)

class InvalidLengthCodecException(length: Int, byteLength: Int) : CodecException(
    "rejected: $length, not equals: $byteLength",
    CodecCode.LENGTH_NOT_EQUALS_READABLE
)

class MissingDelimiterException :
    CodecException("LineDelimiter has missing", CodecCode.MISSING_LINE_DELIMITER)

class OutboundCodecException : CodecException("Outbound Encrypt failed", CodecCode.ENCODE_FAIL)
class SerializeException(message: Throwable) : Exception(message)
class ShortLengthCodecException(rejectedValue: Int, minLength: Int) :
    CodecException(message = "reject $rejectedValue, less than $minLength", CodecCode.SHORT_LENGTH)
