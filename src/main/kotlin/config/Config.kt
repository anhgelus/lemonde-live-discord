package world.anhgelus.lemondelivediscord.config

import kotlinx.serialization.Serializable
import net.peanuuutz.tomlkt.Toml
import net.peanuuutz.tomlkt.TomlComment
import java.nio.file.Paths
import kotlin.io.path.readText

@Serializable
data class Config(
    @TomlComment("""
        Token of the bot
    """)
    val token: String,
    @TomlComment("""
        URL of Le Monde Live
    """)
    val url: String,
    @TomlComment("""
        Period (in minutes) between two fetches
    """)
    val period: Int
) {
    companion object {
        const val fileName = "config.toml"

        fun loadConfig(): Config {
            val tomlString = Paths.get(fileName).readText()
            return Toml.decodeFromString(serializer(), tomlString)
        }
    }
}
