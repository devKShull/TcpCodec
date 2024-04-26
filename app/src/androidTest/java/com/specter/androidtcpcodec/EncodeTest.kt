package com.specter.androidtcpcodec

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.specter.tcpcodec.encoder.HandShakeEncoder
import com.specter.tcpcodec.encoder.OutBoundProtocolEncoder
import com.specter.tcpcodec.model.CodecCode
import com.specter.tcpcodec.model.EncryptionMode
import com.specter.tcpcodec.model.EncryptionPadding
import com.specter.tcpcodec.model.EncryptionType
import com.specter.tcpcodec.model.HandShake
import com.specter.tcpcodec.model.HandShakeHeader
import com.specter.tcpcodec.model.HandShakeResult
import com.specter.tcpcodec.model.HandShakeType
import com.specter.tcpcodec.model.Header
import com.specter.tcpcodec.model.OutboundProtocol
import io.github.ppzxc.crypto.AsymmetricKey
import io.github.ppzxc.crypto.AsymmetricKeyFactory
import io.github.ppzxc.crypto.CryptoFactory
import io.github.ppzxc.crypto.CryptoProvider
import io.github.ppzxc.crypto.Transformation
import org.junit.Test
import org.junit.runner.RunWith
import java.security.spec.X509EncodedKeySpec

@RunWith(AndroidJUnit4::class)
class EncodeTest {
    private val iv = "PF05jFFEqVMpjtR4"
    private val key = "5Y55M6d3vrr8wmnXKVNOlhHEmk7a7Lwm"
    private val jsonMapper = JsonMapper()

    // 임의 키
    private val rsaKey =
        "-----BEGIN PUBLIC KEY-----\nMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVcVmppQgcn82zE+TxxWPyg2ab\nq/95fkjrSe3n0Orw+fKHROMNb8SAPjpCVZXeaFwomw71PsU8JhiHJ91GLj7laXVt\nHMMERFqyBhIexfbSUVL2swdIM0GpACBCr5hFIePqhjhy6L/JZkEsZ4kpMRSCIRHi\nNqIvAyAgTyariqFSAQIDAQAB\n-----END PUBLIC KEY-----"

    @Test
    fun HandShakeEncodeTest() {
        CryptoProvider.BOUNCY_CASTLE.addProvider()

        val crypto =
            CryptoFactory.rsa(AsymmetricKeyFactory.toPublicKey(AsymmetricKey.Type.RSA, rsaKey) {
                X509EncodedKeySpec(it)
            })
        val encoder = HandShakeEncoder(crypto)
        val header = HandShakeHeader(
            length = 0,
            type = HandShakeType.RSA_1024,
            encryptionType = EncryptionType.ADVANCED_ENCRYPTION_STANDARD,
            encryptionMode = EncryptionMode.CIPHER_BLOCK_CHAINING,
            encryptionPadding = EncryptionPadding.PKCS7PADDING,
        )
        val handShake = HandShake(header, iv, key)
        val encoded = encoder.encode(handShake)

        println(encoded.decodeToString())
    }

    @Test
    fun HandShakeResEncodeTest() {
        CryptoProvider.BOUNCY_CASTLE.addProvider()

        val crypto = CryptoFactory.aes(
            key.toByteArray(),
            Transformation.AES_CBC_PKCS7PADDING,
            CryptoProvider.BOUNCY_CASTLE,
            iv.toByteArray()
        )
        val header = Header(
            0,
            12361243L,
            32,
            1,
            2,
            0
        )
        val body = ExampleLogin("tokenExampl ok")

        val outbound = OutboundProtocol(header, body)
        val encoder = OutBoundProtocolEncoder(crypto, jsonMapper)
        val encoded = encoder.encode(outbound)

        println("encoded string ${encoded.decodeToString()}")
        println("encoded string ${encoded.toList()}")
    }
}

data class ExampleLogin(
    val token: String
)