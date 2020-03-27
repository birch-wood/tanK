package net.emdal.tank.clause

class MatchClause(override var queryParts: List<String> = emptyList()) : GraphClause {

  override val verb = "MATCH"
}
