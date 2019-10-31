package net.emdal.tank

import org.neo4j.driver.Session

fun Graph.match(graph: Graph.() -> Graph): Graph {
  return this.copy(query = "$query\nMATCH ".trimIndent()).graph()
}

fun Session.query(block: Graph.() -> Graph) =
  this.run("${Graph().block().query} RETURN *").list().toList()