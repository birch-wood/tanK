package net.emdal.tank

import org.junit.jupiter.api.Test

class CreateQueryTest {

  @Test
  fun `create empty node`() {
    val query = Query().create {
      node()
    }.cypher

    query eq """
      CREATE ()
    """.trimIndent()
  }

  @Test
  internal fun `create node with alias`() {
    val query = Query().create {
      node("n")
    }.cypher

    query eq """
      CREATE (n)
    """.trimIndent()
  }

  @Test
  internal fun `create multiple nodes with different aliases`() {
    val query = Query()
      .create { node("n") }
      .create { node("m") }
      .cypher

    query eq """
      CREATE (n)
      CREATE (m)
    """.trimIndent()
  }

  @Test
  internal fun `create a node with a label`() {
    val query = Query().create {
      node("n", Recipe)
    }.cypher

    query eq """
      CREATE (n:Recipe)
    """.trimIndent()
  }


  @Test
  internal fun `create a node with multiple labels`() {
    val query = Query().create {
      node("n", Recipe, Norwegian)
    }.cypher

    query eq """
      CREATE (n:Recipe:Norwegian)
    """.trimIndent()
  }

  @Test
  internal fun `create node with labels and properties`() {
    val query = Query().create {
      node("n", Norwegian) { town eq "Trondheim" }
    }.cypher

    query eq """
      CREATE (n:Norwegian { town: "Trondheim" })
    """.trimIndent()
  }
}
