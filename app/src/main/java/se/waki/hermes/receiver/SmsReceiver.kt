package se.waki.hermes.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import core.event.SmsEvent

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context?,
        intent: Intent?
    ) {
        context?.let {
            Toast.makeText(it, "SMS received by SENDR", Toast.LENGTH_SHORT).show()
        }

        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)

        for (message in messages) {
            val event = SmsEvent(
                sender = message.originatingAddress ?: "Unknown",
                message = message.messageBody ?: "",
                receivedAt = message.timestampMillis
            )

            val isAlarm =
                event.sender == "3315" &&
                        event.message.contains("Prio", ignoreCase = true) &&
                        event.message.contains("RAPS", ignoreCase = true)

            if (isAlarm) {
                Log.d("Sendr", "ALARM DETECTED")
            }

            Log.d("Sendr", "SMS from: ${event.sender}")
            Log.d("Sendr", "SMS text: ${event.message}")
        }
    }
}