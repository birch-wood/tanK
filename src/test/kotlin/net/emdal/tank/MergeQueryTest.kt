package net.emdal.tank

import org.junit.jupiter.api.Test

class MergeQueryTest {

  @Test
  fun `merge node with string property`() {
    val query = Query().merge {
      node("recipes", Recipe)
        .relationship(MadeFrom)
        .node(Ingredient) { name eq "flour" }
    }.cypher

    query shouldBe """
      MERGE (recipes:Recipe)-[:MADE_FROM]->(:Ingredient { name: "flour" })
    """.trimIndent()
  }

  @Test
  fun `multiline merge query looks sane`() {
    Query().merge {
      node("a", Recipe)
        .relationship(MadeFrom)
        .node(Ingredient) { name eq "sugar" }
    }.merge {
      node("a")
        .relationship(MadeFrom)
        .node(Ingredient) { name eq "flour" }
    }.cypher shouldBe """
      MERGE (a:Recipe)-[:MADE_FROM]->(:Ingredient { name: "sugar" })
      MERGE (a)-[:MADE_FROM]->(:Ingredient { name: "flour" })
    """.trimIndent()
  }


  @Test
  fun `multiple node labels`() {
    Query().merge {
      node("a", Recipe, Ingredient)
    }.cypher shouldBe """
      MERGE (a:Recipe:Ingredient)
    """.trimIndent()
  }

  @Test
  fun `multiple node labels with one label properties set`() {
    Query().merge {
      node("a", Recipe, Ingredient) {
        Recipe.name eq "cake"
      }
    }.cypher shouldBe """
      MERGE (a:Recipe:Ingredient { name: "cake" })
    """.trimIndent()
  }

  @Test
  fun `multiple node labels with both label properties set`() {
    Query().merge {
      node("a", Recipe, Ingredient) {
        (Recipe.name eq "cake") and (Ingredient.taste eq "sweet")
      }
    }.cypher shouldBe """
      MERGE (a:Recipe:Ingredient { name: "cake", taste: "sweet" })
    """.trimIndent()
  }

  @Test
  fun `multiple relationship types with no alias`() {
    Query().merge {
      node()
        .relationship(MadeFrom, IsMainIngredient)
        .node()
    }.cypher shouldBe """
      MERGE ()-[:MADE_FROM|IS_MAIN_INGREDIENT]->()
    """.trimIndent()
  }

  @Test
  fun `multiple relationship types`() {
    Query().merge {
      node()
        .relationship("a", MadeFrom, IsMainIngredient)
        .node()
    }.cypher shouldBe """
      MERGE ()-[a:MADE_FROM|IS_MAIN_INGREDIENT]->()
    """.trimIndent()
  }

  @Test
  fun `multiple relationship types with properties set for both types`() {
    Query().merge {
      node()
        .relationship("a", MadeFrom, IsMainIngredient) {
          (MadeFrom.grams eq 100) and (IsMainIngredient.reason eq "tastes good")
        }
        .node()
    }.cypher shouldBe """
      MERGE ()-[a:MADE_FROM|IS_MAIN_INGREDIENT { grams: 100, reason: "tastes good" }]->()
    """.trimIndent()
  }
}
