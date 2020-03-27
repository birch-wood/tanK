package net.emdal.tank.clause

interface Clause {
  var queryParts: List<String>
  val verb:String
  val cypher:String get() = "$verb ${queryParts.joinToString("")}"
}
