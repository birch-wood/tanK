package net.emdal.tank

import org.neo4j.driver.Value

interface Property {
  val propertyName: String
}

data class StringProperty(override val propertyName: String) : Property
data class IntProperty(override val propertyName: String) : Property

fun string(propertyName: String) = StringProperty(propertyName)
fun int(propertyName: String) = IntProperty(propertyName)

infix fun String.and(newProperty: String) = "$this, $newProperty"

operator fun Value.get(property: StringProperty): String = this[property.propertyName].asString()
operator fun Value.get(property: IntProperty): Int = this[property.propertyName].asInt()
operator fun Value.get(property: IntProperty?): Int? =
  property?.let { this[property.propertyName].asInt() }

