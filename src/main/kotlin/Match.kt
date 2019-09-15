import org.neo4j.driver.Record
import org.neo4j.driver.internal.types.InternalTypeSystem.TYPE_SYSTEM
import kotlin.reflect.KParameter
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor

infix fun String.eq(s: String) = "${this}: \"$s\""

infix fun String.and(a: String) = "$this, $a"

inline fun <reified T : Node> match(alias: String = "n", function1: T.() -> String): Set<T> {
    var toSet = emptySet<T>()
    val label = (T::class.annotations.first { it is Label } as Label).value
    val query = "MATCH ($alias:$label { ${function1.invoke(T::class.createInstance())} }) RETURN $alias"
    driver.session().apply {
        val result = this.run(query)
        toSet = result.list().mapNotNull { record ->
            val args = T::class.primaryConstructor?.parameters?.map {
                find(record, alias, it)
            } ?: emptyList()
            T::class.primaryConstructor?.call(*args.toTypedArray())
        }.toSet()
    }.close()
    return toSet
}

fun find(record: Record, alias: String, it: KParameter): Any? {
    if (it.name == "id") return record[alias].asNode().id()
    val value = record[alias][it.name]
    return when (value.type()) {
       TYPE_SYSTEM.STRING() -> value.asString()
        TYPE_SYSTEM.NULL() -> null
       else -> value.asDouble()
   }
}
