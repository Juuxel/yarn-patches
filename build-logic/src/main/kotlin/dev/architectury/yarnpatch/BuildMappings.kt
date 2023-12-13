package dev.architectury.yarnpatch

import net.fabricmc.mappingio.MappingWriter
import net.fabricmc.mappingio.format.EnigmaReader
import net.fabricmc.mappingio.format.MappingFormat
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files

abstract class BuildMappings : DefaultTask() {
    @get:InputDirectory
    abstract val mappingDirectory: DirectoryProperty

    @get:OutputFile
    abstract val tinyFile: RegularFileProperty

    @TaskAction
    fun run() {
        val inputDir = mappingDirectory.get().asFile.toPath()
        val outputFile = tinyFile.get().asFile.toPath()

        Files.deleteIfExists(outputFile)
        MappingWriter.create(outputFile, MappingFormat.TINY_2).use { writer ->
            EnigmaReader.read(inputDir, "intermediary", "named", writer)
        }
    }
}
