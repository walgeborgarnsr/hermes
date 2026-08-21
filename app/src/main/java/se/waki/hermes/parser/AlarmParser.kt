package se.waki.hermes.parser

import core.event.AlarmEvent

class AlarmParser {

    fun parse(message: String): AlarmEvent? {
        val priorityMatch =
            Regex("""Prio\s*:\s*(\d+)""", RegexOption.IGNORE_CASE)
                .find(message)

        val rapsMatch =
            Regex("""RAPS-(\d+)""", RegexOption.IGNORE_CASE)
                .find(message)

        val priority = priorityMatch?.groupValues?.get(1)?.toIntOrNull()
            ?: return null

        val raps = rapsMatch?.groupValues?.get(1)
            ?: return null

        return AlarmEvent(
            priority = priority,
            raps = raps
        )
    }
}