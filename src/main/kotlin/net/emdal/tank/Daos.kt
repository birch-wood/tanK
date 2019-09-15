package net.emdal.tank

import org.neo4j.driver.Driver
import org.neo4j.driver.Session

abstract class Node

@Target(AnnotationTarget.CLASS)
annotation class Label(val name: String)

@Label("Recipe")
class Recipe(
    val id: Long? = null,
    val name:String? = null
) : Node()

fun <T> transaction(driver: Driver, function: Session.() -> T): T {
    return driver.use { function(it.session()) }
}
