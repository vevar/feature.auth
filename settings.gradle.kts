rootProject.name = "Auth-feature"



object Modules {

    object Feature {

        private val root = ":feature"

        val auth = "${root}:auth"

        val list: List<String> = listOf(auth)

    }

    object Data {
        private const val root = ":sample:data"

        const val auth = "${root}:auth-data"

        val list = listOf(auth)
    }

    object Microservice {
        const val root = ":sample:microservice"


        object Auth {
            const val root = "${Microservice.root}:auth"
            const val grpc = "$root:grpc"
            const val webClient = "$root:web-client"
        }

        val modules = listOf(
            Auth.root,
            Auth.grpc,
            Auth.webClient
        )
    }


    object Tool {
        const val root = ":Kotlin.Tool:tools"

        object Mpp {
            const val root = "${Tool.root}:mpp"

            const val errorHandling = "${root}:error"
            const val api = "${root}:api"
            const val env = "${root}:env"
        }

        object Jvm {
            private const val root = "${Tool.root}:jvm"

            const val webServer = "$root:webServer"
            const val dataBaseManager = "$root:databaseManager"
            const val microservice = "$root:microservice"

            val list = listOf(
                webServer,
                dataBaseManager,
                microservice
            )
        }

        object Js {
            const val root = "${Tool.root}:js"
            const val ui = "$root:ui"
        }
    }


}

Modules.Data.list.forEach { include(it) }
Modules.Microservice.modules.forEach { include(it) }
Modules.Feature.list.forEach { include(it) }

include(Modules.Tool.Mpp.api)
include(Modules.Tool.Mpp.errorHandling)
include(Modules.Tool.Mpp.env)

include(Modules.Tool.Jvm.webServer)
include(Modules.Tool.Jvm.dataBaseManager)

