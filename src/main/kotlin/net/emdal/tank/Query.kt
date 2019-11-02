package net.emdal.tank

import net.emdal.tank.Keyword.CREATE
import net.emdal.tank.Keyword.MATCH
import org.neo4j.driver.Session

fun Session.query(block: Graph.() -> Graph) =
  this.run("${Graph().block().query} RETURN *").list().toList()

fun Graph.keyword(keyword: Keyword): Graph {
  return this.copy(query = "$query\n${keyword.name} ".trimIndent())
}

fun Graph.match(graph: Graph.() -> Graph): Graph {
  return this.keyword(MATCH).graph()
}

fun Graph.create(graph: Graph.() -> Graph): Graph {
  return this.keyword(CREATE).graph()
}

enum class Keyword {
  MATCH,
  CREATE
}
