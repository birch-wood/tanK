package net.emdal.tank.clause

class MergeClause(override var queryParts: List<String> = emptyList()) : GraphClause {

  override val verb = "MERGE"
}
