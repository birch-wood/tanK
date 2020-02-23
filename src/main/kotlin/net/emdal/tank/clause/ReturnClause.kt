package net.emdal.tank.clause

class ReturnClause(override var query: List<String> = listOf("RETURN ")) : Clause {
  fun returnWith(vararg entities: String) = ReturnClause(
    query = query + entities.joinToString(",")
  )
}
