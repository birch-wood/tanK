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
class MatchIntegrationTest {

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
  fun `match fetches string properties of nodes`() {
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
  fun `match fetches int properties of relationships`() {
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

  @Test
  fun `multiline match query fetches apropriate graphs`() {
    driver.session().use { session ->
      session.deleteAllInDatabase()
      session.run(
        """
          CREATE (cake:Recipe { name: "cake" })-[:MADE_FROM { grams: 100 }]->(flour:Ingredient { name: "flour" })
          CREATE (cake)-[:MADE_FROM { grams: 100 }]->(sugar:Ingredient { name: "sugar" })
          CREATE (bread:Recipe { name: "bread" })-[:MADE_FROM { grams: 100 }]->(flour)
        """.trimIndent()
      )

      session.query {
        match {
          node("a", Recipe)
            .relationship(MadeFrom)
            .node(Ingredient) { name eq "sugar" }
        }.match {
          node("a")
            .relationship(MadeFrom)
            .node(Ingredient) { name eq "flour" }
        }
      }.forEach {
        it["a"][Recipe.name] eq "cake"
      }
    }
  }
}