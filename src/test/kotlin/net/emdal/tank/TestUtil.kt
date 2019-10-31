package net.emdal.tank

import org.assertj.core.api.Assertions.assertThat
import org.neo4j.driver.Session

infix fun Any?.eq(other: Any?) {
  assertThat(this).isEqualTo(other)
}

fun Session.deleteAllInDatabase() {
  run("MATCH (n) DETACH DELETE (n);")
}

object Recipe : Node("Recipe") {
  val name = string("name")
}

object Norwegian : Node("Norwegian") {
  val town = string("town")
}

object MadeFrom : Relationship("MADE_FROM") {
  val grams = int("grams").nullable()
}

object Ingredient : Node("Ingredient") {
  val name = string("name")
}

