package net.emdal.tank

import org.junit.jupiter.api.Test

class SetQueryTest {

  @Test
  fun `set int property on a node`() {
    Query()
      .match { node("a", Recipe) { name eq "Cake" } }
      .set("a") { Recipe.calories eq 100 }
      .cypher shouldBe """
      MATCH (a:Recipe { name: "Cake" })
      SET a.calories = 100
    """.trimIndent()
  }

  @Test
  fun `set string property on a node`() {
    Query()
      .match { node("a", Recipe) { calories eq 100 } }
      .set("a") { Recipe.name eq "Cake" }
      .cypher shouldBe """
      MATCH (a:Recipe { calories: 100 })
      SET a.name = "Cake"
    """.trimIndent()
  }

  @Test
  fun `set int property on a relationship`() {
    Query()
      .match { node().relationship("a", MadeFrom) { grams eq 100 }.node() }
      .set("a") { MadeFrom.recipeStep eq 1 }
      .cypher shouldBe """
      MATCH ()-[a:MADE_FROM { grams: 100 }]->()
      SET a.recipeStep = 1
    """.trimIndent()
  }

  @Test
  fun `set string property on a relationship`() {
    Query()
      .match { node().relationship("a", MadeFrom) { grams eq 100 }.node() }
      .set("a") { MadeFrom.description eq "Deglaze pan" }
      .cypher shouldBe """
      MATCH ()-[a:MADE_FROM { grams: 100 }]->()
      SET a.description = "Deglaze pan"
    """.trimIndent()
  }
}