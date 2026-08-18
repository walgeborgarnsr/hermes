package se.waki.hermes.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.provider.Telephony
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context?,
        intent: Intent?) {
        context?.let { Toast.makeText(it, "SMS received by SENDR", Toast.LENGTH_SHORT).show() }
        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)

        for (message in messages) {
        Log.d("Sendr", "SMS from: ${message.originatingAddress}")
        Log.d("Sendr", "SMS text: ${message.messageBody}")
    }

    }
}