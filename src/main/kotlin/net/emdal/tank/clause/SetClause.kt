package net.emdal.tank.clause

import net.emdal.tank.Entity
import net.emdal.tank.IntProperty
import net.emdal.tank.Property
import net.emdal.tank.StringProperty

class SetClause(
  private val alias: String,
  override var query: List<String> = listOf("SET "),
  override var aliases: Map<String, List<Entity>> = emptyMap()
) : Clause {
  infix fun Property.eq(value: Any) = SetClause(
    alias = alias,
    query = query + "$alias.${this.propertyName} = ${format(value)}",
    aliases = aliases
  )

  private fun Property.format(value: Any) = when (this) {
    is IntProperty -> value
    is StringProperty -> "\"$value\""
    else -> value
  }
}
