package world.anhgelus.lemondelivediscord

import net.dv8tion.jda.api.JDABuilder
import world.anhgelus.lemondelivediscord.config.Config
import world.anhgelus.lemondelivediscord.live.LiveParser
import world.anhgelus.lemondelivediscord.utils.Logger
import java.util.*
import kotlin.concurrent.schedule

fun main() {
    val config = Config.loadConfig()

    val jda = JDABuilder.createDefault(config.token)
        .build()

    // block until JDA is ready
    jda.awaitReady()

    Logger.get().info("Bot started")

    val timer = Timer()
    timer.schedule(0L, config.period * 60000L) {
        val parser = LiveParser(config.url)
        var doc = parser.getDocument()
        if (parser.mustRefreshLink(doc)) {
            config.url = parser.getNewLink(doc) ?: return@schedule
            doc = parser.getDocument()
        }
        parser.updateMainImage(doc)
    }
}

