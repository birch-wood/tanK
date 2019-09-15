package net.emdal.tank

import org.assertj.core.api.Assertions.assertThat
import org.neo4j.driver.Config
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.harness.TestServerBuilders
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe


object SingleNodeMatchTest : Spek({

    val db by memoized {
        TestServerBuilders.newInProcessBuilder()
            .newServer()
    }
    lateinit var driver: Driver

    describe("Match tests") {
        beforeGroup { driver = GraphDatabase.driver(db.boltURI()) }
        afterGroup { driver.close() }
        it("Fetch Single Node with a string parameter") {

            class TestEntity(val parameter: String? = null, val expected: String? = null) : Node()

            val parameterValue = "Some string value"
            val expected = "Expected result"

            driver.session()
                .use { session ->
                    session.run(
                        "CREATE (n:MyLabel { parameter: \"$parameterValue\", expected: \"$expected\" } )"
                    )
                }
            val result = transaction(driver) { match(TestEntity(parameter = parameterValue)) }
            assertThat(result.first().expected).isEqualTo(expected)
        }
    }
})
