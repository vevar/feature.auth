import com.google.protobuf.gradle.*


plugins {
    kotlin("jvm")
    id("com.google.protobuf") version "0.8.13"
}


java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Deps.Jvm.kotlinStdlib)

    implementation("javax.annotation:javax.annotation-api:1.2")
    implementation("com.google.protobuf:protobuf-java-util:${Versions.Jvm.protobuf}")
    implementation(Deps.Jvm.grpcProtoBuf)
    implementation(Deps.Jvm.grpcStub)
    implementation(Deps.Jvm.grpcStubKotlin)
    runtimeOnly(Deps.Jvm.grpcNettyShaded)

}

sourceSets {
    main {
        java {
            setSrcDirs(
                listOf(
                    "build/generated/source/proto/main/grpc",
                    "build/generated/source/proto/main/grpckt",
                    "build/generated/source/proto/main/java"
                )
            )

        }
    }
}

protobuf {
    protoc {
        artifact = Deps.Jvm.protobufProtoc
    }
    plugins {
        id("grpc") {
            artifact = Deps.Jvm.grpcProtocGen
        }
        id("grpckt") {
            artifact = Deps.Jvm.grpcProtocGenKotlin
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }


}

