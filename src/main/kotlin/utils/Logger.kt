package world.anhgelus.lemondelivediscord.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Logger {
    fun get(): Logger {
        return LoggerFactory.getLogger("LeMondeLive")!!
    }
}