package world.anhgelus.lemondelivediscord

import net.dv8tion.jda.api.JDABuilder
import org.slf4j.LoggerFactory
import world.anhgelus.lemondelivediscord.config.Config
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

    }
}

