import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES

plugins {

	id("io.spring.dependency-management") version "1.1.3"

	id("org.jetbrains.kotlin.jvm") version "1.9.0"
	id("org.jetbrains.kotlin.plugin.spring") version "1.9.0"

	id("org.springframework.boot") version "3.1.5" apply false

}

group = "br.com.emersonmendes"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
	mavenLocal()
}

apply {
	plugin("org.jetbrains.kotlin.jvm")
	plugin("org.jetbrains.kotlin.plugin.spring")
}

dependencyManagement {

	imports {

		mavenBom("io.github.logrecorder:logrecorder-bom:2.7.0")

		mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.4")
		mavenBom(BOM_COORDINATES)

		mavenBom("org.jetbrains.kotlin:kotlin-bom:1.9.0")
	}

	dependencies {
		dependency("ch.qos.logback:logback-core:1.4.11")
		dependency("ch.qos.logback:logback-classic:1.4.11")
		dependency("org.slf4j:slf4j-api:2.0.9")
		dependency("org.slf4j:slf4j-log4j12:2.0.9")
	}

}

dependencies {

	// IMPLEMENTATION
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.github.resilience4j:resilience4j-spring-boot3")
	implementation("io.github.resilience4j:resilience4j-all") /// OPTIONAL
	implementation("io.github.openfeign:feign-core")
	implementation("io.github.openfeign:feign-gson")
	implementation("io.github.openfeign:feign-okhttp")
	implementation("io.github.openfeign:feign-slf4j")
	implementation("ch.qos.logback:logback-core")
	implementation("ch.qos.logback:logback-classic")
	implementation("org.slf4j:slf4j-api")
	implementation("org.slf4j:slf4j-log4j12")

	// TEST
	testImplementation("io.github.logrecorder:logrecorder-assertions")
	testImplementation("io.github.logrecorder:logrecorder-junit5")
	testImplementation("io.github.logrecorder:logrecorder-logback")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

}

tasks {

	withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf(
				"-Xjsr305=strict",
				"-Xjvm-default=all"
			)
			jvmTarget = "17"
			javaParameters = true
			allWarningsAsErrors = true
		}
	}

	withType<Test> {
		useJUnitPlatform()
	}

}
