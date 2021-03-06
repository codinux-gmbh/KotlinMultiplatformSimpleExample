plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.6.10"
}


repositories {
    maven { setUrl("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}


val ktorVersion = "2.0.0-beta-1"
val kotlinSerializationVersion = "1.3.2"
val coroutinesVersion = "1.6.0"


kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }

    ios {
        binaries {
            framework {
                baseName = "common"
            }
        }
    }

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinSerializationVersion")

                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-server-cors:$ktorVersion")

                implementation("net.dankito.utils:favicon-finder:1.0.0")
                implementation("net.dankito.utils:java-utils:1.0.18")

                implementation("com.soywiz.korlibs.korim:korim:2.4.10")

                // TODO: try to get rid of / use Kotlin MPP logger
                implementation("org.slf4j:slf4j-api:1.7.32")
                implementation("ch.qos.logback:logback-classic:1.2.10")

                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation("io.ktor:ktor-server-tests:$ktorVersion")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:$ktorVersion")
            }
        }

        val jsTest by getting


        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")

                // ktor Native needs "-native-mt" coroutines version. Export it so that referencing applications don't need to import it on their own
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion-native-mt")
            }
        }

        val iosTest by getting


        val nativeMain by getting {
            dependencies {
                // requires that cURL is installed on your system
                implementation("io.ktor:ktor-client-curl:$ktorVersion")

                // ktor Native needs "-native-mt" coroutines version. Export it so that referencing applications don't need to import it on their own
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion-native-mt")
            }
        }

        val nativeTest by getting
    }

}