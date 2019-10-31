package net.emdal.tank

data class Graph(val query: String = "", val aliases: List<Pair<String, Entity?>> = emptyList()) {
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
      query = query + "($alias:${node.label}${node.block()?.let { " { $it}" } ?: ""})",
      aliases = aliases + (alias to node)
    )
  }

  fun node(
    alias: String = "",
    block: () -> String? = { null }
  ): Graph {
    return this.copy(
      query = query + "($alias${block()?.let { " { $it}" } ?: ""})",
      aliases = aliases + (alias to null)
    )
  }
}