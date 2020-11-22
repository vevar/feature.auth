package dev.alxminyaev.auth.web.screen.singin

import dev.alxminyaev.auth.web.screen.loader
import dev.alxminyaev.feature.auth.usecase.AuthUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.kodein.di.DI
import org.kodein.di.instance
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import styled.*
import toUserFriendlyError
import kotlin.coroutines.CoroutineContext

external interface SingInState : RState {
    var login: String
    var password: String
    var isLoading: Boolean
    var error: String
}

external interface SingInProps : RProps {
    var di: DI
}

class SingInScreen : RComponent<SingInProps, SingInState>(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    override fun componentDidMount() {
        setState {
            login = ""
            password = ""
        }
    }


    override fun RBuilder.render() {
        styledDiv {
            css { +SingInStyles.screenContainer }

            styledDiv {
                css { +SingInStyles.form }

                if (state.isLoading) loader()

                styledH1 {
                    css { +SingInStyles.title }
                    +"Авторизация" // TODO think about i18n
                }

                styledDiv {
                    css { +SingInStyles.errorMessage }
                    +state.error
                }

                styledInput {
                    css { +SingInStyles.loginField }
                    attrs {
                        id = "username"
                        name = "username"
                        type = InputType.text
                        placeholder = "Логин" // TODO think about i18n
                        value = state.login
                        onChangeFunction = ::onLoginChanged
                    }
                }

                styledInput {
                    css { +SingInStyles.passwordField }
                    attrs {
                        id = "pass"
                        name = "password"
                        type = InputType.password
                        placeholder = "Пароль" // TODO think about i18n
                        value = state.password
                        onChangeFunction = ::onPasswordChanged
                    }
                }
                styledButton {
                    css { +SingInStyles.singInButton }
                    +"Вход" // TODO think about i18n
                    attrs {
                        onClickFunction = ::onSingInClicked
                    }
                }
            }

        }
    }

    private fun onLoginChanged(event: Event) {
        val element = event.target as HTMLInputElement
        setState { login = element.value }
    }

    private fun onPasswordChanged(event: Event) {
        val element = event.target as HTMLInputElement
        setState { password = element.value }
    }

    private fun onSingInClicked(event: Event) {
        val authUseCase by props.di.instance<AuthUseCase>()
        launch {
            try {
                setState {
                    isLoading = true
                    error = ""
                }
                authUseCase.singIn(state.login, state.password)
                SingInRouter.routerToNext()
            } catch (e: Exception) {
                println(e)
                setState { error = e.toUserFriendlyError().message }
            } finally {
                setState {
                    state.password = ""
                    isLoading = false
                }
            }
        }
    }
}


fun RBuilder.singInScreen(handler: SingInProps.() -> Unit): ReactElement {
    return child(SingInScreen::class) {
        attrs { handler() }
    }
}

