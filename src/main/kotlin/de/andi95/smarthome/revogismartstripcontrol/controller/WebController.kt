package de.andi95.smarthome.revogismartstripcontrol.controller

import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.id
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.script
import kotlinx.html.title
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WebController {

    private val log = LoggerFactory.getLogger(WebController::class.java)!!

    @GetMapping("/")
    fun indexController(): String {
        return renderInitialMarkup()
    }

    fun renderInitialMarkup(): String =
            createHTMLDocument().html {

                head {
                    title { +"Revogi Smartstrip Control" }
                    meta(charset = "utf-8")
                    link(rel = "stylesheet", type = "text/css", href = "/dist/bundle.css") {}
                }

                body {
                    h1 { +"Revogi Smartstrip Control" }
                    div {
                        id = "root"
                    }
                    script(src = "/dist/bundle.js") { }
                }
            }.serialize(true)
}