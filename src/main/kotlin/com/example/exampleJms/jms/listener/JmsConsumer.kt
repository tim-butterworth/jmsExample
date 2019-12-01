package com.example.exampleJms.jms.listener

import javax.jms.Message
import javax.jms.MessageListener

class JmsConsumer : MessageListener {
    override fun onMessage(message: Message?) {
        println(message?.getBody(String::class.java))
    }
}
