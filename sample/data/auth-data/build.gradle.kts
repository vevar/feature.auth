plugins {
    kotlin("multiplatform")
}


kotlin {
    js {
        browser()
    }

    jvm {
        val main by compilations.getting {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    sourceSets {
        js().compilations["main"].defaultSourceSet {
            dependencies {

            }

        }

        jvm().compilations["main"].defaultSourceSet {
            dependencies {

                implementation(Deps.Jvm.jetbrainExposedCore)
                implementation(Deps.Jvm.jetbrainExposedDao)
                implementation(Deps.Jvm.jetbrainExposedJdbc)

                implementation(Deps.Jvm.logback)

                implementation(Deps.Jvm.hikariCp)
                implementation(Deps.Jvm.pgJdbcNg)

//                implementation(project(Modules.Data.user))
            }
        }
    }
}

dependencies {
    mppSetup {
        js = true
        jvm = true

        mppLibrary(Deps.Multiplatform.kotlinStdlib)
        mppLibrary(Deps.Multiplatform.coroutines)
        mppLibrary(Deps.Multiplatform.kodein)
        mppLibrary(Deps.Multiplatform.kodeinConf)

    }
}