/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin library project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.5.1/userguide/building_java_projects.html
 */

group = "cn.repigeons"
version = "1.0.2-RELEASE"

plugins {
    kotlin("jvm") version "1.8.0"
    `maven-publish`
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform(kotlin("bom")))
    // Use the Kotlin JDK 8 standard library.
    api(kotlin("stdlib"))
    // Use the Kotlin test library.
    testImplementation(kotlin("test"))
    // Use the Kotlin JUnit integration.
    testImplementation(kotlin("test-junit"))

    // Project dependencies
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.1")
    implementation("com.google.code.gson:gson:2.10.1")

    // CompileOnly
    compileOnly("org.apache.tomcat.embed:tomcat-embed-core:10.1.5")
    compileOnly("org.springframework.boot:spring-boot:3.0.1")
    compileOnly("org.springframework.data:spring-data-redis:3.0.1")
    compileOnly("org.springframework:spring-context:6.0.4")
    compileOnly("org.springframework:spring-web:6.0.4")
    compileOnly("com.github.pagehelper:pagehelper:5.3.2")

    // annotationProcessor
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor:3.0.1")
    implementation("org.springframework.boot:spring-boot-autoconfigure:3.0.1")
}

publishing {
    val sourceJar = tasks.register("sourcesJar", Jar::class) {
        sourceSets.all { from(allSource) }
        archiveClassifier.convention("sources")
        archiveClassifier.set("sources")
    }
    val javadocJar = tasks.register("javadocJar", Jar::class) {
        archiveClassifier.convention("javadoc")
        archiveClassifier.set(name)
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
            artifact(sourceJar)
            artifact(javadocJar)
        }
    }
    repositories {
        maven {
            if (version.toString().endsWith("-RELEASE")) {
                setUrl("https://mirrors.repigeons.cn/repository/maven-releases/")
            } else if (version.toString().endsWith("-SNAPSHOT")) {
                setUrl("https://mirrors.repigeons.cn/repository/maven-snapshots/")
            }
            credentials {
                username
                password
            }
        }
    }
}
