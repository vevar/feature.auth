plugins {
    id("org.jetbrains.kotlin.js")
    kotlin("plugin.serialization")
}

group = "dev.alxminyaev.service.auth"
version = "0.0.1"

//project.buildDir = File("../build/")

kotlin {
    js {
        browser {
            dceTask {
                keep("ktor-ktor-io.\$\$importsForInline\$\$.ktor-ktor-io.io.ktor.utils.io")
            }
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-js"))
    implementation(Deps.Js.coroutines)

    implementation(Deps.Js.ktorClient)
    implementation(Deps.Js.ktorJson)
    implementation(Deps.Js.ktorSerialization)


    implementation(Deps.Js.kodein)
    implementation(Deps.Js.kodeinConf)

    implementation(Deps.Js.Frontend.kotlinReact)
    implementation(Deps.Js.Frontend.kotlinReactDom)
    implementation(Deps.Js.Frontend.kotlinReactRouting)
    implementation(Deps.Js.Frontend.kotlinStyled)
    implementation(Deps.Js.Frontend.kotlinCss)


    implementation(npm("react", Versions.Js.Frontend.Npm.react))
    implementation(npm("react-dom", Versions.Js.Frontend.Npm.react))
    implementation(npm("react-is", Versions.Js.Frontend.Npm.react))
    implementation(npm("react-router-dom", Versions.Js.Frontend.Npm.reactRouterDom))
    implementation(npm("styled-components", Versions.Js.Frontend.Npm.styledComponents))
    implementation(npm("style-loader", Versions.Js.Frontend.Npm.styleLoader))
    implementation(npm("file-loader", Versions.Js.Frontend.Npm.fileLoader))
    implementation(npm("abort-controller", Versions.Js.Frontend.Npm.abortController))
    implementation(npm("text-encoding", Versions.Js.Frontend.Npm.textEncoding))
    implementation(npm("crypto", Versions.Js.Frontend.Npm.crypto))
    implementation(npm("bufferutil", Versions.Js.Frontend.Npm.bufferutil))
    implementation(npm("utf-8-validate", Versions.Js.Frontend.Npm.utf8Validate))
    implementation(npm("fs", Versions.Js.Frontend.Npm.fs))
    implementation(npm("popper.js", Versions.Js.Frontend.Npm.popperJs))
    implementation(npm("jquery", Versions.Js.Frontend.Npm.jquery))
    implementation(npm("bootstrap", Versions.Js.Frontend.Npm.bootstrap))

    implementation(project(Modules.Tool.Mpp.errorHandling))
    implementation(project(Modules.Tool.Mpp.api))
    implementation(project(Modules.Tool.Mpp.env))

    implementation(project(Modules.Feature.auth))
//    implementation(project(Modules.Feature.shared))
}


