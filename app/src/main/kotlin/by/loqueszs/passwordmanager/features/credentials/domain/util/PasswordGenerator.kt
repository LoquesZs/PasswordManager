package by.loqueszs.passwordmanager.features.credentials.domain.util

import by.loqueszs.passwordmanager.common.util.EMPTY
import kotlin.random.Random

val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
const val STRING_LENGTH = 12

fun getRandomString() = (1..STRING_LENGTH)
    .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
    .joinToString(String.EMPTY)
