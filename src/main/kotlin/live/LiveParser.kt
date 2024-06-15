package world.anhgelus.lemondelivediscord.live

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import world.anhgelus.lemondelivediscord.utils.Logger
import java.util.*

class LiveParser(val url: String) {
    private val logger = Logger.get()

    fun parse(document: Document, lastHour: String): PostData? {
        val last = document
            .getElementsByClass("post__live-section post-container")
            .select("section.post__live-container")
            .first() ?: return null

        val info = last.getElementsByClass("info-content").first()
        val content = last.getElementsByClass("content--live").first()
        if (content == null) {
            logger.warn("Content live is null")
            return null
        }

        val hour = if (info == null) {
            val instance = Calendar.getInstance()
            "${instance.get(Calendar.HOUR_OF_DAY)}: ${instance.get(Calendar.MINUTE)}"
        } else {
            val f = info.getElementsByClass("date").first()
            if (f != null) {
                f.text()
            } else {
                val instance = Calendar.getInstance()
                "${instance.get(Calendar.HOUR_OF_DAY)}: ${instance.get(Calendar.MINUTE)}"
            }
        }

        val title = content
            .getElementsByClass("post__live-container--title post__space-node")
            .first()
            ?.text()
            ?.trim()
            ?: ""

        if (hour.equals(lastHour, true)) {
            logger.debug("No new post")
            return null
        }

        logger.debug("New post")
    }

    fun updateMainImage(document: Document) {
        document.getElementsByClass("hero__live-img").first()?.allElements?.forEach {
            if (it.hasAttr("src")) {
                mainImage = it.attr("src").trim()
                logger.info("Update main image")
            }
        }
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

    companion object {
        var mainImage = ""
            private set

        fun isQuestion(content: Element): Boolean {
            return content
                .allElements
                .stream()
                .anyMatch { element -> element.hasClass("post__live-container--comment-content") }
        }
    }
}