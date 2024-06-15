package world.anhgelus.lemondelivediscord.live

import net.dv8tion.jda.api.EmbedBuilder
import org.jsoup.nodes.Element
import java.awt.Color

data class PostData(
    private var title: String,
    val content: Element,
    val post: Element,
    val lastHour: String
) {
    fun toEmbed() {
        val embed = EmbedBuilder()
        embed.setThumbnail(LiveParser.mainImage)
        var color = if (LiveParser.isQuestion(content)) {
            title = "Vos Questions"
            Color.blue
        } else if (post.hasClass("post__live-container--essential") ||
            post.hasAttr("data-post-essential")) {
            title = "L'Essentiel"

            var msg = content.select("h2").first()!!.text()
            if (msg.length > 256) {
                msg = msg.substring(0, 256 - 3) + "...";
            }
            embed.addField(msg, "", false)

            Color.red
        } else {
            Color.gray
        }
    }
}
