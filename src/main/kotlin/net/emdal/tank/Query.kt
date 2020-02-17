package net.emdal.tank

import net.emdal.tank.clause.*

data class Query(val clauses: List<Clause> = emptyList()) {
  val cypher get() = clauses.joinToString("\n") { it.query.joinToString("") }

  fun match(graph: MatchClause.() -> MatchClause) = this.copy(
    clauses = clauses + MatchClause().graph()
  )

  fun merge(graph: MergeClause.() -> MergeClause) = this.copy(
    clauses = clauses + MergeClause().graph()
  )

  fun create(graph: CreateClause.() -> CreateClause) = this.copy(
    clauses = clauses + CreateClause().graph()
  )

  fun set(alias: String, graph: SetClause.() -> SetClause) = this.copy(
    clauses = clauses + SetClause(alias).graph()
  )

  fun where(alias:String, graph: WhereClause.() -> WhereClause) = this.copy(
    clauses = clauses + WhereClause(alias).graph()
  )

  fun returnWith(vararg alias:String) = this.copy(
    clauses = clauses + ReturnClause().returnWith(*alias)
  )
}
