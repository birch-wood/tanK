package net.emdal.tank

import net.emdal.tank.Clause.CREATE
import net.emdal.tank.Clause.MATCH
import org.neo4j.driver.Session

fun Session.query(block: Graph.() -> Graph) =
  this.run("${Graph().block().query} RETURN *").list().toList()

fun Graph.clause(clause: Clause): Graph {
  return this.copy(query = "$query\n${clause.name} ".trimIndent())
}

fun Graph.match(graph: Graph.() -> Graph): Graph {
  return this.clause(MATCH).graph()
}

fun Graph.create(graph: Graph.() -> Graph): Graph {
  return this.clause(CREATE).graph()
}

enum class Clause {
  MATCH,
  CREATE
}
