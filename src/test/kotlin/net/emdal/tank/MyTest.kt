package net.emdal.tank

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.Neo4jContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MyTest {

    class Neo4kContainer : Neo4jContainer<Neo4kContainer>()

    @Container
    val neo4j: Neo4kContainer = Neo4kContainer().withAdminPassword(null)

    @BeforeAll
    internal fun setUp() {
    }

    // TODO: add tests

    @Test
    fun test() {
        assert(true)
    }
}