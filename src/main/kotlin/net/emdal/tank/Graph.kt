package net.emdal.tank

data class Graph(val query: String = "", val aliases: List<Pair<String, List<Entity>>> = emptyList()) {

  /**
   * Wraps an entity's property constraint string of a query in curly braces if there are any. Otherwise
   * returns an empty string.
   */
  private fun String?.body(): String = this?.let { " { $it }" } ?: ""

  fun <T : Node> node(
    alias: String = "",
    vararg labels: T,
    block: T.() -> String? = { null }
  ): Graph = this.copy(
    query = query + "($alias:${labels.map(Node::label).joinToString(":")}${labels.first().block().body()})",
    aliases = aliases + (alias to labels.toList())
  )

  fun <T : Node> node(
    vararg labels: T,
    block: T.() -> String? = { null }
  ): Graph {
    return this.copy(
      query = query + "(:${labels.map(Node::label).joinToString(":")}${labels.first().block().body()})"
    )
  }

  fun node(
    alias: String = "",
    block: () -> String? = { null }
  ): Graph {
    return this.copy(
      query = query + "($alias${block().body()})",
      aliases = aliases + (alias to emptyList())
    )
  }

  fun relationship(
    alias: String = "",
    block: () -> String? = { null }
  ): Graph {
    return this.copy(
      query = query + "-[$alias${block().body()}]->",
      aliases = aliases + (alias to emptyList())
    )
  }

  fun <T : Relationship> relationship(
    alias: String = "",
    vararg madeFrom: T,
    block: T.() -> String? = { null }
  ): Graph = this.copy(
    query = query + "-[$alias:${madeFrom.map(Relationship::type).joinToString("|")}${madeFrom.first().block().body()}]->",
    aliases = aliases + (alias to madeFrom.toList())
  )

  fun <T : Relationship> relationship(
    vararg relationship: T,
    block: T.() -> String? = { null }
  ): Graph {
    return this.copy(
      query = query + "-[:${relationship.map(Relationship::type).joinToString("|")}${relationship.first().block().body()}]->"
    )
  }
}