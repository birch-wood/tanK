package net.emdal.tank.clause

import net.emdal.tank.IntProperty
import net.emdal.tank.Property
import net.emdal.tank.StringProperty

class SetClause(
  private val alias: String,
  override var query: List<String> = listOf("SET ")
) : Clause {
  infix fun Property.eq(value: Any) = SetClause(
    alias = alias,
    query = query + "$alias.${this.propertyName} = ${format(value)}"
  )

  private fun Property.format(value: Any) = when (this) {
    is IntProperty -> value
    is StringProperty -> "\"$value\""
    else -> value
  }
}
