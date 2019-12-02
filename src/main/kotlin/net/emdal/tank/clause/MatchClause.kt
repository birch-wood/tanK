package net.emdal.tank.clause

import net.emdal.tank.Entity

class MatchClause(
  override var query: List<String> = listOf("MATCH "),
  override var aliases: Map<String, List<Entity>> = emptyMap()
) :
  GraphClause
