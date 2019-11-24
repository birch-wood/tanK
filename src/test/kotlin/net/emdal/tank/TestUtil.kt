package net.emdal.tank

import org.assertj.core.api.Assertions.assertThat
import org.neo4j.driver.Session

infix fun Any?.shouldBe(other: Any?) {
  assertThat(this).isEqualTo(other)
}

fun Session.deleteAllInDatabase() {
  run("MATCH (n) DETACH DELETE (n);")
}

object Recipe : Node("Recipe") {
  val name = string("name")
  val calories = int("calories")
}

object Norwegian : Node("Norwegian") {
  val town = string("town")
}

object MadeFrom : Relationship("MADE_FROM") {
  val grams = int("grams")
  val recipeStep = int("recipeStep")
  val description = string("description")
}

object IsMainIngredient : Relationship("IS_MAIN_INGREDIENT") {
  val reason = string("reason")
}

object Ingredient : Node("Ingredient") {
  val name = string("name")
  val taste = string("taste")
}

