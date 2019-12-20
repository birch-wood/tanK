package net.emdal.tank.clause

import net.emdal.tank.Entity

class MergeClause(
  override var query: List<String> = listOf("MERGE "),
  override var aliases: Map<String, List<Entity>> = emptyMap()
) :
  GraphClause
