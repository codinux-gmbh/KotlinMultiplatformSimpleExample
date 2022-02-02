
rootProject.name = "kotlin-multiplatform-example"


include("common")

include("apps:NativeApp")
findProject(":apps:NativeApp")?.name = "NativeApp"
