package net.emdal.tank

import org.junit.jupiter.api.Test

class WhereClauseTest {

  @Test
  fun `match node where property is over a threshold`() {
    Query()
      .match { node("a", Recipe) { name eq "Cake" } }
      .where("a") { Recipe.calories greaterThan 100 }
      .cypher shouldBe """
      MATCH (a:Recipe { name: "Cake" })
      WHERE a.calories > 100
    """.trimIndent()
  }
}

