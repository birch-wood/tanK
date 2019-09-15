package net.emdal.tank

import org.neo4j.driver.internal.value.StringValue

abstract class Node(val id: Long)

@Target(AnnotationTarget.CLASS)
annotation class Label(val name: String)

@Target(AnnotationTarget.PROPERTY)
annotation class Parameter(val name: String)

@Label("Recipe")
class Recipe(
    id:Long = -1,

    @Parameter("name")
    val name:String = "name"
) : Node(id)
