package net.emdal.tank
import org.neo4j.driver.*
import java.util.*

fun <T> Driver.transaction(function: Session.() -> T) = session().function()
infix fun String.eq(parameterValue: String) = "$this: \"$parameterValue\""
inline fun <reified T> Session.match(filter: T.() -> String): List<Value> {
  val alias = UUID.randomUUID().toString().filterNot { it == '-' || it.isDigit() }
  val s = "MATCH ($alias:${T::class.simpleName} { ${T::class.objectInstance?.filter()} } ) RETURN $alias"
  use { return (it.run(s).list() ?: emptyList()).map { record -> record[alias] } }
}
open class Node(val label: String)
