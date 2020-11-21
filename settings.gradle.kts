rootProject.name = "Auth-feature"



object Modules {

    object Feature {

        private val root = ":feature"

        val auth = "$root:auth"

        val list: List<String> = listOf(auth)

    }

}

Modules.Feature.list.forEach { include(it) }