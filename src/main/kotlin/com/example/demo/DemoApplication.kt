package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import tools.dynamia.app.EnableDynamiaToolsApi
import tools.dynamia.navigation.Module
import tools.dynamia.navigation.ModuleProvider

/**
 * Backend entrypoint for the application. This class is responsible for
 * starting the Spring Boot application and enabling the Dynamia Tools API.
 */
@SpringBootApplication
@EnableDynamiaToolsApi
class DemoApplication {

    /**
     * A minimal starter needs at least one [ModuleProvider] bean: DynamiaTools'
     * `ModuleContainer` requires a non-empty `List<ModuleProvider>` at startup.
     * Replace this with your own module(s)/page(s) as the app grows -- `List<ModuleProvider>`
     * injection is additive, so you can keep this one around or remove it once you add real modules.
     */
    @Bean
    fun homeModuleProvider(): ModuleProvider = ModuleProvider { Module("home", "Home") }

}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
