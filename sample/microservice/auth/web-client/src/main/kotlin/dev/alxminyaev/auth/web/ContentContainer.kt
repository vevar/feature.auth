package dev.alxminyaev.auth.web

import dev.alxminyaev.auth.web.screen.singin.singInScreen
import org.kodein.di.DI
import react.*
import react.router.dom.hashRouter
import react.router.dom.route
import react.router.dom.switch

external interface ContentContainerProps : RProps {
    var di: DI
}

class ContentContainer : RComponent<ContentContainerProps, RState>() {

    override fun RBuilder.render() {

        val di = props.di

        hashRouter {
            switch {
                route("/") {
                    singInScreen {
                        this.di = di
                    }
                }
            }
        }
    }
}

fun RBuilder.contentContainer(handler: ContentContainerProps.() -> Unit): ReactElement {
    return child(ContentContainer::class) {
        attrs { handler() }
    }
}