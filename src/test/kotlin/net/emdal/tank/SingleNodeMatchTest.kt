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

            @Label("MyLabel")
            class TestEntity(id: Long = -1, val parameter:String = "parameter") : Node(id)

            val parameterValue = "Some string value"
            driver.session().use { session -> session.run("CREATE (n:MyLabel { parameter: \"$parameterValue\" } )") }
            val result = transaction(driver) {
                match<TestEntity> {
                    parameter eq parameterValue
                }
            }.first()
            assertThat(result.parameter).isEqualTo(parameterValue)
        }
    }
})
