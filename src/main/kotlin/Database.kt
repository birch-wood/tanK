import org.neo4j.driver.AuthTokens
import org.neo4j.driver.GraphDatabase

val username = "neo4j"
val password = "neo"
val driver =
    GraphDatabase.driver("bolt://localhost:7687"
        , AuthTokens.basic(username, password))

