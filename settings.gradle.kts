rootProject.name = "Antonio"
include(":sample")
include(":antonio")
include(":antonio-databinding")
include(":antonio-paging2")
include(":antonio-paging3")
include(":antonio-core")
include(":antonio-databinding-paging2")
include(":antonio-databinding-paging3")
include(":antonio-core-paging2")
include(":antonio-core-paging3")
include(":antonio-compiler")
include(":antonio-annotation")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}