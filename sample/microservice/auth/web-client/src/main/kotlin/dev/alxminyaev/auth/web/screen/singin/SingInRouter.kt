package dev.alxminyaev.auth.web.screen.singin

import kotlinx.browser.window
import org.w3c.dom.url.URL

internal object SingInRouter {

    fun routerToNext() {
        val url = URL(window.location.href)
        window.location.hash = ""

        val path = url.searchParams.get("path")
        if (path != null) {
            window.location.href = path
        } else {
            window.location.pathname = "/somepath"
        }

    }
}