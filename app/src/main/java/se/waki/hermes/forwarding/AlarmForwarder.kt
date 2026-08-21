package se.waki.hermes.forwarding

import android.telephony.SmsManager

class AlarmForwarder {

    fun forward(
        recipient: String,
        message: String
    ) {
        val smsManager = SmsManager.getDefault()

        smsManager.sendTextMessage(
            recipient,
            null,
            message,
            null,
            null
        )
    }
}