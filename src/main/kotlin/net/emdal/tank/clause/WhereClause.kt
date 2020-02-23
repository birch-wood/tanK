package net.emdal.tank.clause

import net.emdal.tank.Entity
import net.emdal.tank.IntProperty

class WhereClause(
  private val alias: String,
  override var query: List<String> = listOf("WHERE "),
  override var aliases: Map<String, List<Entity>> = emptyMap()
) : Clause {
  infix fun IntProperty.greaterThan(value: Int) = WhereClause(
    alias = alias,
    query = query + "$alias.${this.propertyName} > $value",
    aliases = aliases
  )
}
