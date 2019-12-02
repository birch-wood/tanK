package net.emdal.tank

import org.neo4j.driver.Record
import org.neo4j.driver.Session

fun Session.query(block: Query.() -> Query): List<Record> {
  return this.run("${Query().block().query} RETURN *").list().toList()
}