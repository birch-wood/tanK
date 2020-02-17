package net.emdal.tank

import org.junit.jupiter.api.Test

class ReturnClauseTest {

  @Test
  fun `return a matched node`() {
    Query()
      .match { node("a") }
      .returnWith("a")
      .cypher shouldBe """
      MATCH (a)
      RETURN a
    """.trimIndent()
  }
}
