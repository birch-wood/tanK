package net.emdal.tank

import org.junit.jupiter.api.Test

class MatchQueryTest {

  @Test
  fun `match node with string property`() {
    val query = Graph().match {
      node("recipes", Recipe)
        .relationship(MadeFrom)
        .node(Ingredient) { name eq "flour" }
    }.query

    query eq """
      MATCH (recipes:Recipe)-[:MADE_FROM]->(:Ingredient { name: "flour" })
    """.trimIndent()
  }

  @Test
  internal fun `multiline match query looks sane`() {
    Graph().match {
      node("a", Recipe)
        .relationship(MadeFrom)
        .node(Ingredient) { Ingredient.name eq "sugar" }
    }.match {
      node("a")
        .relationship(MadeFrom)
        .node(Ingredient) { Ingredient.name eq "flour" }
    }.query eq """
      MATCH (a:Recipe)-[:MADE_FROM]->(:Ingredient { name: "sugar" })
      MATCH (a)-[:MADE_FROM]->(:Ingredient { name: "flour" })
    """.trimIndent()
  }
}
