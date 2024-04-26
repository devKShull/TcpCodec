package com.specter.tcpcodec.model

interface Protocol {
    val header: Header
}

class InboundProtocol(
    override val header: Header,
    val body: ByteArray
) : Protocol {
}

class OutboundProtocol(
    override val header: Header,
    val body: Any?
) : Protocol {

}