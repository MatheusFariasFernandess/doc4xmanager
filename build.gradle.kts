plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.docx4j:docx4j:6.1.2")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.1")
}

tasks.test {
    useJUnitPlatform()
}