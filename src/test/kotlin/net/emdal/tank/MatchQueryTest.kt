package net.emdal.tank

import org.junit.jupiter.api.Test

class MatchQueryTest {

  @Test
  fun `match node with string property`() {
    val query = Query().match {
      node("recipes", Recipe)
        .relationship(MadeFrom)
        .node(Ingredient) { name eq "flour" }
    }.cypher

    query shouldBe """
      MATCH (recipes:Recipe)-[:MADE_FROM]->(:Ingredient { name: "flour" })
    """.trimIndent()
  }

  @Test
  fun `multiline match query looks sane`() {
    Query().match {
      node("a", Recipe)
        .relationship(MadeFrom)
        .node(Ingredient) { name eq "sugar" }
    }.match {
      node("a")
        .relationship(MadeFrom)
        .node(Ingredient) { name eq "flour" }
    }.cypher shouldBe """
      MATCH (a:Recipe)-[:MADE_FROM]->(:Ingredient { name: "sugar" })
      MATCH (a)-[:MADE_FROM]->(:Ingredient { name: "flour" })
    """.trimIndent()
  }


  @Test
  fun `multiple node labels`() {
    Query().match {
      node("a", Recipe, Ingredient)
    }.cypher shouldBe """
      MATCH (a:Recipe:Ingredient)
    """.trimIndent()
  }

  @Test
  fun `multiple node labels with one label properties set`() {
    Query().match {
      node("a", Recipe, Ingredient) {
        Recipe.name eq "cake"
      }
    }.cypher shouldBe """
      MATCH (a:Recipe:Ingredient { name: "cake" })
    """.trimIndent()
  }

  @Test
  fun `multiple node labels with both label properties set`() {
    Query().match {
      node("a", Recipe, Ingredient) {
        (Recipe.name eq "cake") and (Ingredient.taste eq "sweet")
      }
    }.cypher shouldBe """
      MATCH (a:Recipe:Ingredient { name: "cake", taste: "sweet" })
    """.trimIndent()
  }

  @Test
  fun `multiple relationship types with no alias`() {
    Query().match {
      node()
        .relationship(MadeFrom, IsMainIngredient)
        .node()
    }.cypher shouldBe """
      MATCH ()-[:MADE_FROM|IS_MAIN_INGREDIENT]->()
    """.trimIndent()
  }

  @Test
  fun `multiple relationship types`() {
    Query().match {
      node()
        .relationship("a", MadeFrom, IsMainIngredient)
        .node()
    }.cypher shouldBe """
      MATCH ()-[a:MADE_FROM|IS_MAIN_INGREDIENT]->()
    """.trimIndent()
  }

  @Test
  fun `multiple relationship types with properties set for both types`() {
    Query().match {
      node()
        .relationship("a", MadeFrom, IsMainIngredient) {
          (MadeFrom.grams eq 100) and (IsMainIngredient.reason eq "tastes good")
        }
        .node()
    }.cypher shouldBe """
      MATCH ()-[a:MADE_FROM|IS_MAIN_INGREDIENT { grams: 100, reason: "tastes good" }]->()
    """.trimIndent()
  }
}
