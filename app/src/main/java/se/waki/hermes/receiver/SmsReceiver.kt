package se.waki.hermes.receiver
import se.waki.hermes.forwarding.AlarmForwarder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import core.event.SmsEvent
import se.waki.hermes.parser.AlarmParser

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
                val alarm = AlarmParser().parse(event.message)

                Log.d("Sendr", "ALARM DETECTED")
                Log.d("Sendr", "Priority: ${alarm?.priority}")
                Log.d("Sendr", "RAPS: ${alarm?.raps}")

                AlarmForwarder().forward(
                    recipient = "5551234",
                    message = event.message
                )
            }

            Log.d("Sendr", "SMS from: ${event.sender}")
            Log.d("Sendr", "SMS text: ${event.message}")
        }
    }
}