pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.architectury.dev")
    }
}

rootProject.name = "yarn-patches"
includeBuild("build-logic")

include("forge-1.20.4")
