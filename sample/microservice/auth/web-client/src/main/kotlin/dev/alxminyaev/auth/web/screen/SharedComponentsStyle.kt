package dev.alxminyaev.auth.web.screen

import kotlinx.css.*
import styled.StyleSheet

object SharedComponentsStyle : StyleSheet(name = "SharedComponentsStyle") {

    val screenTitle by css {
        position = Position.relative
        fontFamily = "Roboto-Regular"
        fontSize = 48.px
        color = ColorsStyle.primary
    }

    val screenTitleContainer by css {
        position = Position.relative
        paddingBottom = 8.px
        paddingLeft = 8.px
        paddingRight = 8.px
        borderBottomWidth = 1.px
        borderBottomColor = ColorsStyle.border
        borderBottomStyle = BorderStyle.solid
    }

    val screenContainer by css {
        height = 100.pct
        width = 100.pct
        position = Position.relative
        paddingLeft = 8.px
        paddingRight = 8.px
        overflow = Overflow.auto

        media("all and (min-width: ${Sizes.tabletWidth})") {
            paddingLeft = 32.px
            paddingRight = 32.px
        }

        paddingTop = 27.px
    }

    val loader by css {
        classes = mutableListOf("loader")
    }
}