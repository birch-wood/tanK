package net.emdal.tank

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.testcontainers.containers.Neo4jContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MatchTest {

  class Neo4kContainer : Neo4jContainer<Neo4kContainer>()

  lateinit var neo4j: Neo4kContainer

  lateinit var driver: Driver

  @BeforeAll
  internal fun setUp() {
    neo4j = Neo4kContainer().withAdminPassword(null)
    neo4j.start()
    println("${neo4j.isRunning} container is running!11")
    driver = GraphDatabase.driver(neo4j.boltUrl, AuthTokens.none())
  }

  @AfterAll
  internal fun tearDown() {
    neo4j.stop()
  }

  @Test
  fun `match node with string property`() {
    driver.session().use { session ->

      session.deleteAllInDatabase()
      session.run(
        """
          CREATE (c:Recipe { name: "cake" })-[:MADE_FROM { grams: 100 }]->(f:Ingredient { name: "flour" })
        """.trimIndent()
      )

      session.query {
        match {
          node("recipes", Recipe)
            .relationship(MadeFrom)
            .node(Ingredient) { name eq "flour" }
        }
      }.forEach {
        it["recipes"][Recipe.name] eq "cake"
      }
    }
  }

  @Test
  fun `match relationship with int property`() {
    driver.session().use { session ->
      session.deleteAllInDatabase()
      session.run(
        """
          CREATE (c:Recipe { name: "cake" })-[:MADE_FROM { grams: 100 }]->(f:Ingredient { name: "flour" })
        """.trimIndent()
      )

      session.query {
        match {
          node(Recipe)
            .relationship("madeOf", MadeFrom)
            .node(Ingredient) { name eq "flour" }
        }
      }.forEach {
        it["madeOf"][MadeFrom.grams] eq 100
      }
    }
  }
}