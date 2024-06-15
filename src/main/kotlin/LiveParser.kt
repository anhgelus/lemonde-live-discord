package world.anhgelus.lemondelivediscord

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import world.anhgelus.lemondelivediscord.utils.Logger

class LiveParser(val url: String) {
    val logger = Logger.get()

    fun parse(document: Document) {

    }

    fun getDocument(): Document {
        return Jsoup.connect(url).get()
    }

    fun getNewLink(document: Document): String? {
        val last = document.getElementsByClass("post post__live-container").first()
        if (last == null) {
            logger.info("No new link found, waiting...")
            return null
        }
        val elem = last.getElementsByClass("post__live-container--link").first()
        if (elem != null) {
            val link = elem.attr("href")
            logger.info("New link: $link")
            return link
        }
        for (el in last.allElements) {
            if (el.hasAttr("href")) {
                val link = el.attr("href")
                if (link.contains("live")) {
                    logger.info("New link: $link")
                    return link
                }
            }
        }
        logger.warn("Last do not have a link")
        return null
    }

    fun mustRefreshLink(document: Document): Boolean {
        return document.getElementsByClass("label__live label__live--off").size > 0
    }
}