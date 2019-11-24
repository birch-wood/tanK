package net.emdal.tank

import net.emdal.tank.clause.Clause
import net.emdal.tank.clause.CreateClause
import net.emdal.tank.clause.MatchClause
import net.emdal.tank.clause.SetClause

data class Query(val clauses: List<Clause> = emptyList()) {
  val cypher get() = clauses.joinToString("\n") { it.query.joinToString("") }

  fun match(graph: MatchClause.() -> MatchClause) = this.copy(
    clauses = clauses + MatchClause().graph()
  )

  fun create(graph: CreateClause.() -> CreateClause) = this.copy(
    clauses = clauses + CreateClause().graph()
  )

  fun set(alias: String, graph: SetClause.() -> SetClause) = this.copy(
    clauses = clauses + SetClause(alias).graph()
  )

}
