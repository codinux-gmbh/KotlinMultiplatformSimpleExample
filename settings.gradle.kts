rootProject.name = "kotlin-multiplatform-example"


include("common")

include("apps:NativeApp")
findProject(":apps:NativeApp")?.name = "NativeApp"

include("apps:AndroidApp")
findProject(":apps:AndroidApp")?.name = "BankFinderAndroid"

include("apps:WebApp")
findProject(":apps:WebApp")?.name = "WebApp"
