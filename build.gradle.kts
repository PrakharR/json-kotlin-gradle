/*
 * @(#) build.gradle.kts
 */

@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "io.github.prakharr"
version = "0.68.7"
description = "Gradle Code Generation Plugin for JSON Schema"

val displayName = "JSON Schema Code Generation Plugin"
val projectURL = "https://github.com/prakharr/${project.name}"

plugins {
    kotlin("jvm") version("1.5.20")
    id("org.jetbrains.dokka") version "1.5.0"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    `kotlin-dsl`
    `maven-publish`
    signing
}

repositories {
    mavenCentral()
}

tasks {
    withType<KotlinCompile>().configureEach {
        kotlinOptions {
            languageVersion = "1.5"
            jvmTarget = "1.8"
        }
    }
    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }
    val javadocJar by creating(Jar::class) {
        archiveClassifier.set("javadoc")
        from(dokkaJavadoc)
        dependsOn(dokkaJavadoc)
    }
    artifacts {
        add("archives", sourcesJar)
        add("archives", javadocJar)
    }
    dokkaJavadoc {
        outputDirectory.set(file("$buildDir/javadoc"))
        dokkaSourceSets.configureEach {
            reportUndocumented.set(false)
            jdkVersion.set(8)
            perPackageOption {
                matchingRegex.set(".*\\.internal($|\\.).*")
                suppress.set(true)
            }
        }
    }
    javadoc {
        enabled = false
    }
    named<Jar>("javadocJar").configure {
        from(dokkaJavadoc)
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("gradle-plugin-api"))
    implementation("io.github.prakharr:json-kotlin-schema:0.31.2")
    implementation("io.github.prakharr:json-kotlin-schema-codegen:0.68.7")
    implementation("net.pwall.json:jsonutil:5.0")
    implementation("net.pwall.json:json-pointer:2.2")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

publishing {
    publications {
        afterEvaluate {
            named<MavenPublication>("pluginMaven") {
                artifact(file("build/libs/${project.name}-${project.version}-sources.jar")) {
                    classifier = "sources"
                }
                artifact(file("build/libs/${project.name}-${project.version}-javadoc.jar")) {
                    classifier = "javadoc"
                }
                pom {
                    name.set(displayName)
                    url.set(projectURL)
                    description.set(project.description)
                    packaging = "jar"
                    scm {
                        connection.set("scm:git:git://github.com/prakharr/${project.name}.git")
                        url.set(projectURL)
                    }
                    licenses {
                        license {
                            name.set("The MIT License (MIT)")
                            url.set("http://opensource.org/licenses/MIT")
                        }
                    }
                    developers {
                        developer {
                            id.set("pr_revent@hotmail.com")
                            name.set("Prakhar Rathi")
                            email.set("pr_revent@hotmail.com")
                            url.set("https://prakharrathi.com")
                            roles.set(setOf("lead", "developer"))
                            timezone.set("Asia/Singapore")
                        }
                        developer {
                            id.set("pwall@pwall.net")
                            name.set("Peter Wall")
                            email.set("pwall@pwall.net")
                            url.set("https://pwall.net")
                            roles.set(setOf("architect", "developer"))
                            timezone.set("Australia/Sydney")
                        }
                    }
                }
            }
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            username.set(project.findProperty("ossrhUsername").toString())
            password.set(project.findProperty("ossrhPassword").toString())
        }
    }
}

signing {
    afterEvaluate {
        useGpgCmd()
        sign(*publishing.publications.toTypedArray())
    }
}
