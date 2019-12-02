package net.emdal.tank.clause

import net.emdal.tank.Entity

class CreateClause(
  override var query: List<String> = listOf("CREATE "),
  override var aliases: Map<String, List<Entity>> = emptyMap()
) :
  GraphClause