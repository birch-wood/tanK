package neo4k

import org.assertj.core.api.Assertions.assertThat
import org.neo4j.driver.Config
import org.neo4j.driver.GraphDatabase
import org.neo4j.harness.TestServerBuilders
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe


object DslDesignSpekIT : Spek({
  val driverConfig = Config.builder().withoutEncryption().build()
  val db by memoized {
    TestServerBuilders.newInProcessBuilder()
      .newServer()
  }
  beforeEachTest { }
  describe("something") {
    it("does something") {
      GraphDatabase.driver(db.boltURI()).use {
        it.session().use { session ->
          val result = session.run("MATCH (n) RETURN count(n) AS x")

          assertThat(result.list())
            .hasSize(1)
            .extracting { r ->
              val node = r.get("x")
              node.asInt()
            }
            .containsExactly(0)
        }
      }
    }
  }
})