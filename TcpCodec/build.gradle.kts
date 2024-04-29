plugins {
    alias(libs.plugins.jetbrainsKotlinJvm)
    `java-library`
    `maven-publish`
}

java {
    withJavadocJar()
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

publishing {
    publications{
        create<MavenPublication>("maven"){
            groupId = "io.github.devkshull"
            artifactId = "tcpcodec"
            version = "0.0.1"

            from(components["java"])

            pom{
                name = "Tcp Codec"
                description = "Kotlin Primitive Tcp Codec for using android"
                url = "https://github.com/devKShull/TcpCodec.git"

                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "KShull"
                        name = "devKShull"
                        email = "rhkstn0303@gmail.com"
                    }
                }
            }
        }
    }
}

dependencies {
    implementation(libs.crypto)
}