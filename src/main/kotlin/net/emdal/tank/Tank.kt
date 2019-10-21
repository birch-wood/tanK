package net.emdal.tank

import org.neo4j.driver.Session
import org.neo4j.driver.Value

open class Entity
open class Node(val label: String) : Entity()
open class Relationship(val name: String) : Entity()
data class StringProperty(val propertyName:String)
data class IntProperty(val propertyName:String)

data class Graph(val query: String = "", val aliases: List<Pair<String, Entity>> = emptyList())

fun string(propertyName: String) = StringProperty(propertyName)
fun int(propertyName: String) = IntProperty(propertyName)
fun IntProperty.nullable():IntProperty? = this
fun StringProperty.nullable():StringProperty? = this

fun Graph.match(graph: Graph.() -> Graph): Graph {
  return this.copy(query = "MATCH ").graph()
}

infix fun StringProperty.eq(value:String):String {
  return """${this.propertyName}: "$value" """
}

fun <T : Relationship> Graph.relationship(
  alias: String = "",
  madeFrom: T,
  block: T.() -> String = { "" }
): Graph = relationship(madeFrom, alias, block)

fun <T : Relationship> Graph.relationship(
  relationship: T,
  alias: String = "",
  block: T.() -> String = { "" }
): Graph {
  return this.copy(query = query + "-[$alias:${relationship.name} {${relationship.block()}}]->", aliases = aliases + (alias to relationship))
}

fun <T : Node> Graph.node(
  alias: String = "",
  node: T,
  block: T.() -> String = { "" }
): Graph = node(node, alias, block)

fun <T : Node> Graph.node(
  node: T,
  alias: String = "",
  block: T.() -> String = { "" }
): Graph {
  return this.copy(query = query + "($alias:${node.label} {${node.block()}})", aliases = aliases + (alias to node))
}

fun Session.query(block: Graph.() -> Graph)
    = this.run("${Graph().block().query} RETURN *").list().toList()

operator fun Value.get(property: StringProperty):String = this[property.propertyName].asString()
operator fun Value.get(property: IntProperty):Int = this[property.propertyName].asInt()
operator fun Value.get(property: IntProperty?):Int? = property?.let { this[property.propertyName].asInt() }
