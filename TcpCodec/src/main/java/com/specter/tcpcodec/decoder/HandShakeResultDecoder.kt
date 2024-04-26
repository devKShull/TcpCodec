package com.specter.tcpcodec.decoder

import com.specter.tcpcodec.model.CodecCode
import com.specter.tcpcodec.model.HandShakeResult
import java.nio.ByteBuffer

object HandShakeResultDecoder : BaseDecoder(HandShakeResult.LENGTH) {

    fun decode(msg: ByteArray): CodecCode {
        val buffer = ByteBuffer.wrap(msg)
        checkConditionAndGetLength(buffer)
        val result = buffer.get()
        return CodecCode.of(result)
    }
}