package net.emdal.tank.clause

import net.emdal.tank.IntProperty
import net.emdal.tank.Property
import net.emdal.tank.StringProperty

class SetClause(
  private val alias: String,
  override var queryParts: List<String> = emptyList()
) : Clause {

  override val verb = "SET"

  infix fun Property.eq(value: Any) = SetClause(
    alias = alias,
    queryParts = queryParts + "$alias.${this.propertyName} = ${format(value)}"
  )

  private fun Property.format(value: Any) = when (this) {
    is IntProperty -> value
    is StringProperty -> "\"$value\""
    else -> value
  }
}
