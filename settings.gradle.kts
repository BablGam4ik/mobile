pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    // ЕДИНСТВЕННЫЙ вызов from() для versionCatalogs
    //versionCatalogs {
    // create("libs") {
    //   from(files("gradle/libs.versions.toml"))
    // }
    // }
//}

    rootProject.name = "My Application"
    include(":app")
}