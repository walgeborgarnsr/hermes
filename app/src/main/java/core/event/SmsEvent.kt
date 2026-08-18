package core.event

data class SmsEvent(
    val sender: String,
    val message: String,
    val receivedAt: Long
)