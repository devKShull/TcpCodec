package com.specter.tcpcodec

object Constants {
    object Crypto {
        val SYMMETRIC_KEY_SIZE = intArrayOf(16, 24, 32)
    }

    object LineDelimiter {
        const val STRING = "\r\n"
        val BYTE_ARR = STRING.toByteArray()
        val LENGTH = BYTE_ARR.size
    }
}