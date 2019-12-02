package net.emdal.tank

import org.neo4j.driver.Value

data class StringProperty(val propertyName: String)

data class IntProperty(val propertyName: String)

fun string(propertyName: String) = StringProperty(propertyName)

fun int(propertyName: String) = IntProperty(propertyName)

fun IntProperty.nullable(): IntProperty? = this

fun StringProperty.nullable(): StringProperty? = this

infix fun StringProperty.eq(value: String): String {
  return """${this.propertyName}: "$value""""
}

infix fun IntProperty?.eq(value: Int): String {
  return """${this?.propertyName}: $value"""
}

infix fun String.and(newProperty: String) = "$this, $newProperty"

operator fun Value.get(property: StringProperty): String = this[property.propertyName].asString()
operator fun Value.get(property: IntProperty): Int = this[property.propertyName].asInt()
operator fun Value.get(property: IntProperty?): Int? =
  property?.let { this[property.propertyName].asInt() }

