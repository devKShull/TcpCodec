package com.specter.tcpcodec.decoder

import com.specter.tcpcodec.DecryptCodecException
import com.specter.tcpcodec.model.CodecCode
import com.specter.tcpcodec.model.Header
import com.specter.tcpcodec.model.InboundProtocol
import io.github.ppzxc.crypto.Crypto
import java.nio.ByteBuffer

class InboundProtocolDecoder(
    private val crypto: Crypto,
    override val minimumBytes: Int = Header.MINIMUM_LENGTH
) : BaseDecoder(minimumBytes) {

    fun decode(byteArray: ByteArray): InboundProtocol {
        val buffer = ByteBuffer.wrap(byteArray)

        val length = checkConditionAndGetLength(buffer)

        // Length 필드를 제외한 암호화된 본문 가져옴
        val encryptedBody = ByteArray(buffer.limit() - Header.LENGTH_FIELD_LENGTH)
        buffer.get(encryptedBody)

        // 복호화
        val decrypted = getDecrypted(encryptedBody)

        // 헤더 및 body object 생성
        val header = getHeader(length, decrypted)
        val body = getBody(decrypted)

        return InboundProtocol(header, body)
    }

    private fun getDecrypted(msg: ByteArray): ByteBuffer {
        return try {
            ByteBuffer.wrap(crypto.decrypt(msg))
        } catch (exception: Exception) {
            throw DecryptCodecException(msg.decodeToString(), CodecCode.DECRYPT_FAIL)
        }
    }

    private fun getHeader(length: Int, decrypted: ByteBuffer): Header {
        return Header(
            length = length,
            id = decrypted.getLong(),
            type = decrypted.get(),
            status = decrypted.get(),
            encoding = decrypted.get(),
            reserved = decrypted.get()
        )
    }

    private fun getBody(decrypted: ByteBuffer): ByteArray {
        val bodyArray = ByteArray(decrypted.limit() - (Header.ID_FIELD_LENGTH + Header.PROTOCOL_FIELDS_LENGTH))
        decrypted.get(bodyArray)
        return bodyArray
    }

}