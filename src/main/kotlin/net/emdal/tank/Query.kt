package net.emdal.tank

import net.emdal.tank.clause.Clause
import net.emdal.tank.clause.CreateClause
import net.emdal.tank.clause.MatchClause

data class Query(val clauses: List<Clause> = emptyList()) {
  val query get() = clauses.joinToString("\n") { it.query.joinToString("") }

  fun match(graph: MatchClause.() -> MatchClause) = this.copy(
    clauses = clauses + MatchClause().graph()
  )

  fun create(graph: CreateClause.() -> CreateClause)= this.copy(
    clauses = clauses + CreateClause().graph()
  )
}
