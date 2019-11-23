package net.emdal.tank.clause

import net.emdal.tank.Entity

interface Clause {
  var query: List<String>
  var aliases: Map<String, List<Entity>>
}