plugins {
    id("java")
    application
    kotlin("jvm")
}

group = "sample.auth"

application {
    mainClassName = "dev.alxminyaev.microservice.auth.MainKt"
}

kotlin {
    target {
        compilations["main"].kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}
dependencies {
    implementation(Deps.Jvm.kotlinStdlib)
    implementation(Deps.Jvm.kotlinHTML)
    implementation(Deps.Jvm.ktorHtmlBuilder)

    implementation(Deps.Jvm.ktorServetNetty)
    implementation(Deps.Jvm.ktorAuthJwt)

    implementation(Deps.Jvm.kodein)
    implementation(Deps.Jvm.kodeinConf)

    implementation(Deps.Jvm.jetbrainExposedCore)
    implementation(Deps.Jvm.jetbrainExposedDao)
    implementation(Deps.Jvm.jetbrainExposedJdbc)

    implementation(Deps.Jvm.hikariCp)
    implementation(Deps.Jvm.pgJdbcNg)

    implementation(project(Modules.Tool.Mpp.env))
    implementation(project(Modules.Tool.Jvm.webServer))
    implementation(project(Modules.Tool.Jvm.dataBaseManager))

    implementation(project(Modules.Feature.auth))

    implementation(project(Modules.Data.auth))
//    implementation(project(Modules.Data.user))
//    implementation(project(Modules.Data.shared))

    implementation(Deps.Jvm.grpcProtoBuf)
    implementation(Deps.Jvm.grpcStub)
    implementation(Deps.Jvm.grpcStubKotlin)

    implementation(project(Modules.Microservice.Auth.grpc))
//    implementation(project(Modules.Microservice.User.grpc))

}

apply(from = "manifest.gradle")