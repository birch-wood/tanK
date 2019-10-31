package net.emdal.tank

data class Graph(val query: String = "", val aliases: List<Pair<String, Entity?>> = emptyList()) {

  /**
   * Wraps an entity's property constraint string of a query in curly braces if there are any. Otherwise
   * returns an empty string.
   */
  private fun String?.body(): String = this?.let { " { $it}" } ?: ""

  fun <T : Node> node(
    alias: String = "",
    node: T,
    block: T.() -> String? = { null }
  ): Graph = node(node, alias, block)

  fun <T : Node> node(
    node: T,
    alias: String = "",
    block: T.() -> String? = { null }
  ): Graph {
    return this.copy(
      query = query + "($alias:${node.label}${node.block().body()})",
      aliases = aliases + (alias to node)
    )
  }

  fun node(
    alias: String = "",
    block: () -> String? = { null }
  ): Graph {
    return this.copy(
      query = query + "($alias${block().body()})",
      aliases = aliases + (alias to null)
    )
  }

  fun relationship(
    alias: String = "",
    block: () -> String? = { null }
  ): Graph {
    return this.copy(
      query = query + "-[$alias${block().body()}]->",
      aliases = aliases + (alias to null)
    )
  }

  fun <T : Relationship> relationship(
    alias: String = "",
    madeFrom: T,
    block: T.() -> String? = { null }
  ): Graph = relationship(madeFrom, alias, block)

  fun <T : Relationship> relationship(
    relationship: T,
    alias: String = "",
    block: T.() -> String? = { null }
  ): Graph {
    return this.copy(
      query = query + "-[$alias:${relationship.name}${relationship.block().body()}]->",
      aliases = aliases + (alias to relationship)
    )
  }
}