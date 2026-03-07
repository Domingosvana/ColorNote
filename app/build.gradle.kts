plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    //alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    //alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt.android)
    //alias(libs.plugins.hilt.android)
    kotlin("plugin.serialization") version libs.versions.kotlin


}

android {




    namespace = "com.colornote"
    compileSdk = 35


    // ✅ ADICIONE ISSO:
    androidResources {
        localeFilters.addAll(listOf("en", "es","pt", "pt-rBR","pt-rPT"))
    }

    defaultConfig {
        applicationId = "com.colornote"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"



        /*
        Framework de testes: Para testes instrumentados (que rodam em dispositivo/emulador)
        Alternativa moderna:
        */
        testInstrumentationRunner = "androidx.compose.ui.test.junit4.AndroidComposeTestRunner"

        /*
        Vetores: Habilita suporte a imagens vetoriais
        Vantagem: Escalabilidade sem perda de qualidade
        Economia: Reduz tamanho do APK
          "xxhdpi", "xxxhdpi", "xhdpi"
         */
        vectorDrawables.useSupportLibrary = true

    }

    buildTypes {
        release {
            isDebuggable = false      // ✅ Debugging desligado (segurança)
            isMinifyEnabled = true // ✅ Ativar minificação
            isShrinkResources = true // ✅ Remover recursos não usados

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isDebuggable = true       // ✅ Debugging habilitado
            isMinifyEnabled = false
            isShrinkResources = false
        }



    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }


    kotlinOptions {
        jvmTarget = "17"

        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-Xjvm-default=all",
            "-Xstring-concat=inline",

            //  EXTRA: Outras otimizações úteis
            "-Xjsr305=strict",           // Melhor null-safety
            "-Xassertions=jvm",          // Assertions em produção
            "-Xemit-jvm-type-annotations",// Melhor interoperabilidade

            "-Xjsr305=strict",           // ✅ Null-safety mais rigorosa
            "-Xcontext-receivers",       // ✅ Habilita context receivers
            "-Xinline-classes",          // ✅ Otimização de value classes
            "-Xnew-inference"            // ✅ Melhor inferência de tipos
        )
    }



    buildFeatures {
        compose = true    // ✅ Para UI moderna
        buildConfig = true // ✅ Para configurações
    }

    // Amanhã pode mudar para
    // ✅ VERSÃO ALINHADA:
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11" // Para Kotlin 1.9.20
    }



    packaging {
        resources {
            excludes += setOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "/META-INF/*.MD",
                "/META-INF/*.txt",
                "/META-INF/NOTICE*",

                // ✅ APENAS estas exclusões seguras:
                "**/kotlin/annotation/**",
                "**/kotlin/build.txt",
                "**/kotlin/package.html",

                "**/*.proto",
                "**/version.properties",
                "**/DEPENDENCIES",
                "**/INDEX.LIST",
                "**/DebugProbesKt.bin",
                "**/java.compiler/**"

                // ❌ NÃO exclua collections/, io/, coroutines/
            )
        }
    }







// Crie uma task personalizada para remover densidades específicas
    tasks.register("removeUnusedDensities") {
        doLast {
            delete("src/main/res/drawable-mdpi")
            delete("src/main/res/drawable-hdpi")
            delete("src/main/res/drawable-xhdpi")
            // Mantenha apenas xxhdpi e xxxhdpi
        }
    }





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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.kotlinx.serialization.json)

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    implementation("androidx.compose.ui:ui-text-google-fonts:1.7.4")

    // Desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

}