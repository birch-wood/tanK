package net.emdal.tank

import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.Session

fun main() {
    val username = "neo4j"
    val password = "neo"
    val driver =
        GraphDatabase.driver(
            "bolt://localhost:7687"
            , AuthTokens.basic(username, password)
        )

    val asd = transaction(driver) {
        match<Recipe> {
            name eq "Cookies"
        }
    }.map { it.id }
    println(asd)
}

class TankSession(val session: Session)

fun <T> transaction(driver: Driver, function: TankSession.() -> T): T {
    return driver.use { function(TankSession(it.session())) }
}

