package net.emdal.tank.clause

import net.emdal.tank.Entity

class ReturnClause(
  override var query: List<String> = listOf("RETURN "),
  override var aliases: Map<String, List<Entity>> = emptyMap()
) : Clause {
  fun returnWith(vararg entities:String) = ReturnClause(
    query = query + entities.joinToString(",")
  )
}
