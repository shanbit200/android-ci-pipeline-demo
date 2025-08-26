plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("jacoco")
}

android {
    namespace = "com.shan.pipelinedemo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.shan.pipelinedemo"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        testInstrumentationRunnerArguments["disableAnalytics"] = "true"
    }

    buildTypes {
        getByName("debug") {
            enableAndroidTestCoverage = true // enables coverage for connectedDebugAndroidTest
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
        animationsDisabled = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}
jacoco {
    toolVersion = "0.8.10"
}
// Paths to execution data from both unit and androidTest
val unitTestExec = fileTree(project.layout.buildDirectory).include(
    "**/jacoco/testDebugUnitTest.exec", // older AGP
    "**/jacoco/testDebugUnitTest.exec*",
    "**/jacoco/*.exec"
)
val androidTestEc = fileTree(buildDir).include(
    "outputs/code_coverage/*AndroidTest/connected/**/*.ec",
    "outputs/code-coverage/connected/*/*.ec",           // AGP 8.x
    "outputs/unit_test_code_coverage/*.ec"              // vendor quirks
)

// Directories to include in coverage (exclude generated/irrelevant classes)
val fileFilter = listOf(
    "**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*",
    "**/*_MembersInjector.class", "dagger/**", "hilt_aggregated_deps/**",
    "**/Dagger*.*", "**/*_Factory*.*", "**/*_Provide*Factory*.*",
    "**/*Preview*.*", "**/*ComposableSingletons*.*" // optional: keep only if you truly don't want these
)



// Main source sets (adjust if you use multi-module)
val kotlinTree = fileTree("${project.projectDir}/src/main/java") { setExcludes(fileFilter) }
val kotlinKtsTree = fileTree("${project.projectDir}/src/main/kotlin") { setExcludes(fileFilter) }
val classTree = fileTree("${buildDir}/intermediates/javac/debug/classes") { setExcludes(fileFilter) }
val kotlinClassTree = fileTree("${buildDir}/tmp/kotlin-classes/debug") { setExcludes(fileFilter) }

// Combined JaCoCo report task
tasks.register<JacocoReport>("jacocoFullReport") {
    dependsOn("testDebugUnitTest", "connectedDebugAndroidTest")


    executionData(unitTestExec, androidTestEc)


    reports {
        xml.required.set(true)
        xml.outputLocation.set(file("${buildDir}/reports/jacoco/jacocoFullReport/jacocoFullReport.xml"))
        html.required.set(true)
        html.outputLocation.set(file("${buildDir}/reports/jacoco/jacocoFullReport/html"))
        csv.required.set(false)
    }


    classDirectories.setFrom(files(classTree, kotlinClassTree))
    sourceDirectories.setFrom(files("src/main/java", "src/main/kotlin"))
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.ui.test.junit4)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(kotlin("test"))

}