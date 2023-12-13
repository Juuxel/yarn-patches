package dev.architectury.yarnpatch

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import javax.inject.Inject

abstract class YarnPatchExtension {
    @get:Inject
    protected abstract val repositories: RepositoryHandler

    @get:Inject
    protected abstract val dependencies: DependencyHandler

    fun minecraft(version: String) {
        dependencies.add("minecraft", "net.minecraft:minecraft:$version")
    }

    fun forge(version: String) {
        dependencies.add("forge", "net.minecraftforge:forge:$version")
    }

    fun neoForge(version: String) {
        repositories.maven {
            setUrl("https://maven.neoforged.net/releases/")
        }

        dependencies.add("neoForge", "net.neoforged:neoforge:$version")
    }
}
