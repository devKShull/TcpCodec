package com.specter.tcpcodec.decoder

import com.specter.tcpcodec.BlankBodyCodecException
import com.specter.tcpcodec.Constants
import com.specter.tcpcodec.InvalidLengthCodecException
import com.specter.tcpcodec.MissingDelimiterException
import com.specter.tcpcodec.ShortLengthCodecException
import java.nio.ByteBuffer
import java.util.Arrays

open class BaseDecoder(
    open val minimumBytes: Int
) {
    /**
     * 바이트 배열의 끝에 lineDelimiter 가 포함 되었는지 체크
     */
    private fun checkLineDelimiter(byteBuffer: ByteBuffer) {
        val delimiter = Arrays.copyOfRange(
            byteBuffer.array(),
            byteBuffer.capacity() - Constants.LineDelimiter.LENGTH,
            byteBuffer.capacity()
        )

        if (delimiter.decodeToString() != Constants.LineDelimiter.STRING) {
            throw MissingDelimiterException()
        }
    }

    protected fun checkConditionAndGetLength(msg: ByteBuffer): Int {
        if (msg.capacity() == 0) {
            //빈 byte 체크
            throw BlankBodyCodecException("byte array is empty")
        }

        if (msg.capacity() < minimumBytes) {
            //최소 byte 수 체크
            throw ShortLengthCodecException(msg.capacity(), minimumBytes)
        }

        checkLineDelimiter(msg)

        val length = msg.getInt()
        if (length != msg.remaining()) {
            throw InvalidLengthCodecException(length, msg.remaining())
        }

        return length
    }
}