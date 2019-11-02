package net.emdal.tank

import org.junit.jupiter.api.Test

class CreateQueryTest {

  @Test
  fun `create empty node`() {
    val query = Graph().create {
      node()
    }.query

    query eq """
      CREATE ()
    """.trimIndent()
  }

  @Test
  internal fun `create node with alias`() {
    val query = Graph().create {
      node("n")
    }.query

    query eq """
      CREATE (n)
    """.trimIndent()
  }

  @Test
  internal fun `create multiple nodes with different aliases`() {
    val query = Graph()
      .create { node("n") }
      .create { node("m") }
      .query

    query eq """
      CREATE (n)
      CREATE (m)
    """.trimIndent()
  }

  @Test
  internal fun `create a node with a label`() {
    val query = Graph().create {
      node("n", Recipe)
    }.query

    query eq """
      CREATE (n:Recipe)
    """.trimIndent()
  }


  @Test
  internal fun `create a node with multiple labels`() {
    val query = Graph().create {
      node("n", Recipe, Norwegian)
    }.query

    query eq """
      CREATE (n:Recipe:Norwegian)
    """.trimIndent()
  }

  @Test
  internal fun `create node with labels and properties`() {
    val query = Graph().create {
      node("n", Norwegian) { town eq "Trondheim" }
    }.query

    query eq """
      CREATE (n:Norwegian { town: "Trondheim" })
    """.trimIndent()
  }
}
