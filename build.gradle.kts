plugins {
    kotlin("jvm") version "2.0.0"
}

group = "me.axiumyu.toibe"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kandy-lets-plot:0.8.0-RC1") // Kandy 绘图库
    implementation("org.jsoup:jsoup:1.16.1") // 用于 HTML 数据解析
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}