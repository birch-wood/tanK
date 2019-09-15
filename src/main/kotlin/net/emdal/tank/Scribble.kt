package net.emdal.tank

import org.neo4j.driver.AuthTokens
import org.neo4j.driver.GraphDatabase

fun main() {
    val username = "neo4j"
    val password = "neo"
    val driver =
        GraphDatabase.driver(
            "bolt://localhost:7687"
            , AuthTokens.basic(username, password)
        )

    val kake = transaction(driver) { match(Recipe(name = "Cookies")) }.map { it.name }
    println(kake)
}
