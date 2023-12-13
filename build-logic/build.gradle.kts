plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net")
    maven("https://maven.minecraftforge.net")
    maven("https://maven.architectury.dev")
}

dependencies {
    api("dev.architectury:architectury-loom:1.4.+")
    implementation("net.fabricmc:mapping-io:0.4.2")
}
