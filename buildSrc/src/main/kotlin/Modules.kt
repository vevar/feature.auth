import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

object Modules {

    object App {
        const val amartWeb = ":app:web-client"
        const val amartBackEnd = ":app:server"
        val apps = listOf(amartWeb, amartBackEnd)
    }

    const val grpc = ":grpc"
    const val domain = ":domain"
    const val database = ":data"

    object Data {
        const val root = ":data"
        const val product = "${root}:product"
        const val auth = "${root}:auth"
        const val user = "${root}:user"
        const val file = "${root}:file"
        const val shared = "${root}:shared"

        val dataModules = listOf(root, product, auth, user, file, shared)


        fun DependencyHandlerScope.dataJvmModuleWithShared(module: String) {
            "jvmImplementation"(project(module))
            "jvmImplementation"(project(shared))
        }
    }

    object Feature {

        const val feature = ":feature"
        const val shared = "${feature}:shared"
        const val navigation = "${feature}:navigation"

        object File {
            private const val root = "${feature}:file"
            const val image = "${root}:image"
        }


        const val welcome = "${feature}:welcome"
        const val contacts = "${feature}:contacts"
        const val about = "${feature}:about"
        const val brand = "${feature}:brand"

        const val catalog = "${feature}:catalog"
        const val auth = "${feature}:auth"
        const val user = "${feature}:user"

        val features = arrayOf(
            feature,
            navigation,
            shared,
            welcome,
            about,
            contacts,
            brand,
            catalog,
            auth,
            user,
            File.image
        )
    }

    object Microservice {
        const val root = ":microservice"

        const val env = "$root:env"
        const val catalog = "$root:catalog"
        const val auth = "$root:auth"

        object User {
            const val root = "${Microservice.root}:user"
            const val grpc = "${root}:grpc"
        }

        object Catalog {
            const val root = "${Microservice.root}:catalog"
            const val adminWebClient = "${root}:admin-web-client"
        }

        object Auth {
            const val root = "${Microservice.root}:auth"
            const val grpc = "$root:grpc"
            const val webClient = "$root:web-client"
        }

        object Management {
            const val root = "${Microservice.root}:management"
            const val adminWebClient = "$root:admin-web-client"
        }

        val modules = arrayOf(
            Catalog.root,
            Catalog.adminWebClient,
            User.root,
            User.grpc,
            Auth.root,
            Auth.grpc,
            Auth.webClient,
            Management.root,
            Management.adminWebClient
        )
    }

    object Tool {
        const val root = ":tools"

        object Mpp {
            const val root = "${Tool.root}:mpp"

            const val errorHandling = "${root}:error"
            const val api = "${root}:api"

        }

        object Jvm {
            const val root = "${Tool.root}:jvm"

            const val webServer = "${Tool.Jvm.root}:webServer"
            const val microservice = "${Tool.Jvm.root}:microservice"
        }

        object Js {
            const val root = "${Tool.root}:js"
            const val ui = "$root:ui"
        }
    }

}

