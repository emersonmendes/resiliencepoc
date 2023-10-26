import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES

plugins {

	id("io.spring.dependency-management") version "1.1.2"
//	id("io.spring.dependency-management") version "1.1.3"

//	id("org.jetbrains.kotlin.jvm") version "1.8.22"
//	id("org.jetbrains.kotlin.plugin.spring") version "1.8.22"

	id("org.jetbrains.kotlin.jvm") version "1.7.22"
	id("org.jetbrains.kotlin.plugin.spring") version "1.7.22"

	//	id("org.springframework.boot") version "3.1.5" apply false

	id("org.springframework.boot") version "2.7.14" apply false
}

group = "br.com.emersonmendes"
version = "0.0.1-SNAPSHOT"

//java {
//	sourceCompatibility = JavaVersion.VERSION_17
//}

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

		mavenBom("org.springframework.cloud:spring-cloud-dependencies:2021.0.8")
		mavenBom(BOM_COORDINATES)

//		mavenBom("org.springframework.boot:spring-boot-dependencies:3.1.5")
//		mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.4")

//		mavenBom("io.github.openfeign:feign-bom:13.0")

// NOT HERE
//		mavenBom("io.github.resilience4j:resilience4j-bom:2.1.0")
//		mavenBom("io.github.resilience4j:resilience4j-bom:1.7.0")

		mavenBom("io.github.logrecorder:logrecorder-bom:2.7.0")
		mavenBom("io.github.openfeign:feign-bom:10.7.0")

//		mavenBom("org.jetbrains.kotlin:kotlin-bom:1.7.22")
		mavenBom("org.jetbrains.kotlin:kotlin-bom:1.7.22")
	}

	dependencies {
		// springboot 3
//		dependency("ch.qos.logback:logback-core:1.4.11")
//		dependency("ch.qos.logback:logback-classic:1.4.11")
//		dependency("org.slf4j:slf4j-api:2.0.9")
//		dependency("org.slf4j:slf4j-log4j12:2.0.9")

		// springboot 2
		dependency("ch.qos.logback:logback-core:1.1.7")
		dependency("ch.qos.logback:logback-classic:1.1.7")
		dependency("org.slf4j:slf4j-api:1.7.10")
		dependency("org.slf4j:slf4j-log4j12:1.7.10")
	}

}

dependencies {

	// IMPLEMENTATION
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.github.resilience4j:resilience4j-spring-boot2")
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
