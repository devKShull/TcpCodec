package com.specter.androidtcpcodec

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.specter.tcpcodec.decoder.InboundProtocolDecoder
import io.github.ppzxc.crypto.CryptoFactory
import io.github.ppzxc.crypto.CryptoProvider
import io.github.ppzxc.crypto.Transformation
import org.junit.Test
import org.junit.runner.RunWith
import java.nio.ByteBuffer

@RunWith(AndroidJUnit4::class)
class DecodeTest {
    private val iv = "LwsicCaqQCc7PUNr"
    private val key = "1Qme3tqdoX6MYogetaAMvkivbDKxXk9L"


    @Test
    fun decodeByteData() {
        val inboundMock = byteArrayOf(
            0x00,
            0x00,
            0x00,
            0x1a,
            0x34,
            0x78,
            0x51,
            0x4f,
            0x53,
            0x2b,
            0x37,
            0x53,
            0x75,
            0x69,
            0x4c,
            0x34,
            0x75,
            0x31,
            0x6b,
            0x70,
            0x45,
            0x76,
            0x79,
            0x61,
            0x51,
            0x77,
            0x3d,
            0x3d,
            0x0d,
            0x0a
        )
        println("inboundString ${inboundMock.decodeToString()}")
        val crypto = CryptoFactory.aes(
            key.toByteArray(),
            Transformation.AES_CBC_PKCS7PADDING,
            CryptoProvider.BOUNCY_CASTLE,
            iv.toByteArray()
        )
        val decoder = InboundProtocolDecoder(crypto)

        val result = decoder.decode(inboundMock)

        println("decode result ")
        println("${result.body.toList()}")
        println("length: ${result.header.length}")
        println("id: ${result.header.id}")
        println("type: ${result.header.type}")
        println("status: ${result.header.status}")
        println("encoding: ${result.header.encoding}")
        println("reserved: ${result.header.reserved}")
    }

    @Test
    fun bufferTest() {
        val inboundMock = byteArrayOf(
            0x00,
            0x00,
            0x00,
            0x1a,
            0x34,
            0x78,
            0x51,
            0x4f,
            0x53,
            0x2b,
            0x37,
            0x53,
            0x75,
            0x69,
            0x4c,
            0x34,
            0x75,
            0x31,
            0x6b,
            0x70,
            0x45,
            0x76,
            0x79,
            0x61,
            0x51,
            0x77,
            0x3d,
            0x3d,
            0x0d,
            0x0a
        )

        val buffer = ByteBuffer.wrap(inboundMock)
        println("original ${buffer.array().toList()}")
        val length = buffer.getInt()

        val sliced = ByteArray(buffer.limit() - 4)
        buffer.get(sliced)
        println("sliced ${sliced.toList()}")
    }

}