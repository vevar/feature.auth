package dev.alxminyaev.auth.web.screen.singin

import dev.alxminyaev.auth.web.screen.SharedComponentsStyle
import kotlinx.css.*
import kotlinx.css.properties.transform
import kotlinx.css.properties.translateY
import styled.StyleSheet

object SingInStyles : StyleSheet("SingInStyles") {

    val screenContainer by css {
        +SharedComponentsStyle.screenContainer
        display = Display.flex
        flexDirection = FlexDirection.column
        alignItems = Align.center
    }

    val form by css {
        position = Position.absolute
        top = 50.pct
        transform {
            translateY((-50).pct)
        }
        maxWidth = 250.px
        display = Display.flex
        flexDirection = FlexDirection.column
        width = 100.pct
    }

    private val item by css {
        height = 40.px
        width = 100.pct
        marginBottom = 8.px
        textAlign = TextAlign.center
    }

    val title by css {
        +item
    }

    val errorMessage by css {
        width = 100.pct
        marginBottom = 8.px
        textAlign = TextAlign.center
        color = Color.red
    }

    val loginField by css {
        +item
    }

    val passwordField by css {
        +item
    }

    val singInButton by css {
        +item
    }
}