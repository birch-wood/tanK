package net.emdal.tank.clause

import net.emdal.tank.IntProperty

class WhereClause(
  private val alias: String,
  override var query: List<String> = listOf("WHERE ")
) : Clause {
  infix fun IntProperty.greaterThan(value: Int) = WhereClause(
    alias = alias,
    query = query + "$alias.${this.propertyName} > $value"
  )
}
