plugins {
    id("java")
}

group = "com.github.paopaoyue"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Copy>("deployToMODFolder") {
    dependsOn(tasks.jar)
    from(layout.buildDirectory.dir("libs"))
    include("*.jar")
    into(layout.buildDirectory.dir("E:\\Steam Games\\steamapps\\common\\SlayTheSpire\\mods"))
}
