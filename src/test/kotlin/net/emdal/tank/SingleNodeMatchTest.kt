package net.emdal.tank

import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.harness.TestServerBuilders
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object TestEntity : Node("TestEntity") {
  val parameter: String = "parameter"
  val expected: String = "expected"
}

object SingleNodeMatchTest : Spek({

  val db by memoized {
    TestServerBuilders.newInProcessBuilder()
      .newServer()
  }
  lateinit var driver: Driver

  describe("Match tests") {
    beforeEachTest { driver = GraphDatabase.driver(db.boltURI()) }
    afterEachTest { driver.close() }

    it("Fetch Single Node with a string parameter") {

      val parameterValue = "Some string value"
      val expected = "Expected result"

      val result = driver.transaction {
        run("CREATE (n:TestEntity { parameter: \"$parameterValue\", expected: \"$expected\" } )")
        match<TestEntity> {
          parameter eq parameterValue
        }
      }
      result.first()[TestEntity.expected].asString() isEqualTo expected
    }
  }
})
