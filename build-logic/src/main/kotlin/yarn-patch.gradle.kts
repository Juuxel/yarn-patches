import dev.architectury.yarnpatch.BuildMappings
import dev.architectury.yarnpatch.YarnPatchExtension
import net.fabricmc.loom.LoomGradleExtension
import net.fabricmc.loom.api.LoomGradleExtensionAPI
import net.fabricmc.loom.api.mappings.layered.MappingsNamespace

plugins {
    id("dev.architectury.loom")
}

val enigmaRuntime = configurations.create("enigmaRuntime") {
    isCanBeConsumed = false
    isCanBeResolved = true
}

val loom = extensions.getByName<LoomGradleExtensionAPI>("loom")

dependencies {
    // Set up empty mapping layer
    "mappings"(loom.layered {})
    enigmaRuntime("cuchaz:enigma-swing:2.3.3")
}

// Disable unnecessary build outputs (we're not compiling anything)
tasks {
    named("jar") {
        enabled = false
    }

    named("remapJar") {
        enabled = false
    }
}

extensions.create<YarnPatchExtension>("yarnPatch")

val mappingDir = file("mappings")
val builtTiny = layout.buildDirectory.file("mappings.tiny")

tasks {
    register<JavaExec>("enigma") {
        mainClass.set("cuchaz.enigma.gui.Main")
        classpath = enigmaRuntime
        jvmArgs("-Xmx2G")

        args(
            "-jar",
            (loom as LoomGradleExtension)
                .getMinecraftJars(MappingsNamespace.NAMED)
                .single()
                .toAbsolutePath(),
            "-mappings",
            mappingDir.absolutePath
        )
    }

    register<BuildMappings>("buildMappings") {
        mappingDirectory.set(mappingDir)
        tinyFile.set(builtTiny)
    }

    register<Jar>("mappingsJar") {
        from(builtTiny) {
            into("mappings")
        }

        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
}
