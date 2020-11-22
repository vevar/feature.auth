package dev.alxminyaev.auth.web.screen

import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.css
import styled.styledDiv

// Need move to tools (the ui module)
class LoaderComponent : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                position = Position.absolute
                top = 50.pct
                width = 100.pct
                height = 100.pct
            }
            styledDiv { css { +SharedComponentsStyle.loader } }
        }

    }
}

fun RBuilder.loader() = child(LoaderComponent::class) {}