package dev.alxminyaev.microservice.auth.di

import com.alxminyaev.grpc.auth.AuthGrpcKt
import com.alxminyaev.grpc.auth.ContainAccessTokenRequest
import com.alxminyaev.grpc.auth.ContainAccessTokenResponse
import com.alxminyaev.tool.error.exceptions.UnauthorizedException
import dev.alxminyaev.feature.auth.usecase.AuthUseCase
import io.grpc.BindableService
import io.ktor.http.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val api = DI.Module(name = "api") {
        bind<BindableService>("authService") with singleton {
            object : AuthGrpcKt.AuthCoroutineImplBase() {
                override suspend fun containAccessToken(request: ContainAccessTokenRequest): ContainAccessTokenResponse {
                    val builder = ContainAccessTokenResponse.newBuilder()
                    val authUseCase = instance<AuthUseCase>()
                    return try {
                        val user = authUseCase.identification(request.accessToken)
                        val userBuilder = builder.userBuilder
                        builder.user = userBuilder.setId(user.id).build()
                        builder.code = HttpStatusCode.OK.value
                        builder
                    } catch (e: UnauthorizedException) {
                        builder.code = HttpStatusCode.Unauthorized.value
                        builder
                    } catch (e: Exception) {
                        builder.code = HttpStatusCode.InternalServerError.value
                        builder
                    }.build()
                }
            }
        }
    }
