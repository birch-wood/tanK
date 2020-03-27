package net.emdal.tank.clause

class ReturnClause(override var queryParts: List<String> = emptyList()) : Clause {

  override val verb = "RETURN"

  fun returnWith(vararg entities: String) = ReturnClause(
    queryParts = queryParts + entities.joinToString(",")
  )
}
