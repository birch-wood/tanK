package net.emdal.tank.clause

class CreateClause(override var queryParts: List<String> = emptyList()) : GraphClause {

  override val verb = "CREATE"
}
