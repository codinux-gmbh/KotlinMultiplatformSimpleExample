plugins {
    kotlin("multiplatform")
}


kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                baseName = "FaviconFinder"

                entryPoint = "main"
            }
        }
    }


    sourceSets {
        val commonMain by getting {
            dependencies {
              implementation(project(":common"))
            }
        }

        val nativeMain by getting
    }
}