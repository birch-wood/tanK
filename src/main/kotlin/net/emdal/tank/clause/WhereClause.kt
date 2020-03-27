package net.emdal.tank.clause

import net.emdal.tank.IntProperty

class WhereClause(
  private val alias: String,
  override var queryParts: List<String> = emptyList()
) : Clause {
  override val verb = "WHERE"

  infix fun IntProperty.greaterThan(value: Int) = WhereClause(
    alias = alias,
    queryParts = queryParts + "$alias.${this.propertyName} > $value"
  )
}
