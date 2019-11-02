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
        .node(Ingredient) { name eq "sugar" }
    }.match {
      node("a")
        .relationship(MadeFrom)
        .node(Ingredient) { name eq "flour" }
    }.query eq """
      MATCH (a:Recipe)-[:MADE_FROM]->(:Ingredient { name: "sugar" })
      MATCH (a)-[:MADE_FROM]->(:Ingredient { name: "flour" })
    """.trimIndent()
  }


  @Test
  internal fun `multiple node labels`() {
    Graph().match {
      node("a", Recipe, Ingredient)
    }.query eq """
      MATCH (a:Recipe:Ingredient)
    """.trimIndent()
  }

  @Test
  internal fun `multiple node labels with one label properties set`() {
    Graph().match {
      node("a", Recipe, Ingredient) {
        Recipe.name eq "cake"
      }
    }.query eq """
      MATCH (a:Recipe:Ingredient { name: "cake" })
    """.trimIndent()
  }

  @Test
  internal fun `multiple node labels with both label properties set`() {
    Graph().match {
      node("a", Recipe, Ingredient) {
        (Recipe.name eq "cake") and (Ingredient.taste eq "sweet")
      }
    }.query eq """
      MATCH (a:Recipe:Ingredient { name: "cake", taste: "sweet" })
    """.trimIndent()
  }

  @Test
  internal fun `multiple relationship types with no alias`() {
    Graph().match {
      node()
        .relationship(MadeFrom, IsMainIngredient)
        .node()
    }.query eq """
      MATCH ()-[:MADE_FROM|IS_MAIN_INGREDIENT]->()
    """.trimIndent()
  }

  @Test
  internal fun `multiple relationship types`() {
    Graph().match {
      node()
        .relationship("a", MadeFrom, IsMainIngredient)
        .node()
    }.query eq """
      MATCH ()-[a:MADE_FROM|IS_MAIN_INGREDIENT]->()
    """.trimIndent()
  }

  @Test
  internal fun `multiple relationship types with properties set for both types`() {
    Graph().match {
      node()
        .relationship("a", MadeFrom, IsMainIngredient) {
          (MadeFrom.grams eq 100) and (IsMainIngredient.reason eq "tastes good")
        }
        .node()
    }.query eq """
      MATCH ()-[a:MADE_FROM|IS_MAIN_INGREDIENT { grams: 100, reason: "tastes good" }]->()
    """.trimIndent()
  }
}
