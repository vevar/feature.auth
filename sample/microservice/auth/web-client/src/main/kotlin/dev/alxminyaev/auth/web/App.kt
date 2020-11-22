package dev.alxminyaev.auth.web

import org.kodein.di.DI
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState

external interface AppProps : RProps {
    var di: DI
}

class App : RComponent<AppProps, RState>() {

    override fun RBuilder.render() {
        contentContainer {
            di = props.di
        }
    }
}