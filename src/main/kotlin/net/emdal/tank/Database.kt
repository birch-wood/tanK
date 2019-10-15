package net.emdal.tank

import org.neo4j.driver.AuthTokens
import org.neo4j.driver.GraphDatabase


fun driver(
  uri: String = "bolt://localhost:7687",
  username: String = "neo4j",
  password: String = "testing"
) = GraphDatabase.driver(uri, AuthTokens.basic(username, password))

fun session() = driver().session()