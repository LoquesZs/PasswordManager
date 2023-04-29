package by.loqueszs.passwordmanager.utils

import java.time.DateTimeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

internal val mainFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss")

fun String?.toLocalDateTime(formatter: DateTimeFormatter = mainFormatter): LocalDateTime {
    return try {
        LocalDateTime.parse(this, formatter)
    } catch (e: DateTimeParseException) {
        LocalDateTime.now()
    }
}

fun LocalDateTime.toFormattedString(formatter: DateTimeFormatter = mainFormatter): String {
    return try {
        formatter.format(this)
    } catch (e: DateTimeException) {
        formatter.format(LocalDateTime.now())
    }
}
