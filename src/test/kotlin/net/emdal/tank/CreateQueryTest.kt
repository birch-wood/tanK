package net.emdal.tank

import org.junit.jupiter.api.Test

class CreateQueryTest {

  @Test
  fun `create empty node`() {
    val query = Query().create {
      node()
    }.cypher

    query shouldBe """
      CREATE ()
    """.trimIndent()
  }

  @Test
  fun `create node with alias`() {
    val query = Query().create {
      node("n")
    }.cypher

    query shouldBe """
      CREATE (n)
    """.trimIndent()
  }

  @Test
  fun `create multiple nodes with different aliases`() {
    val query = Query()
      .create { node("n") }
      .create { node("m") }
      .cypher

    query shouldBe """
      CREATE (n)
      CREATE (m)
    """.trimIndent()
  }

  @Test
  fun `create a node with a label`() {
    val query = Query().create {
      node("n", Recipe)
    }.cypher

    query shouldBe """
      CREATE (n:Recipe)
    """.trimIndent()
  }


  @Test
  fun `create a node with multiple labels`() {
    val query = Query().create {
      node("n", Recipe, Norwegian)
    }.cypher

    query shouldBe """
      CREATE (n:Recipe:Norwegian)
    """.trimIndent()
  }

  @Test
  fun `create node with labels and properties`() {
    val query = Query().create {
      node("n", Norwegian) { town eq "Trondheim" }
    }.cypher

    query shouldBe """
      CREATE (n:Norwegian { town: "Trondheim" })
    """.trimIndent()
  }
}
