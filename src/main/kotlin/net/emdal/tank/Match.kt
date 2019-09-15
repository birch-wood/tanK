package net.emdal.tank

import org.neo4j.driver.Record
import org.neo4j.driver.Session
import org.neo4j.driver.internal.types.InternalTypeSystem.TYPE_SYSTEM
import kotlin.reflect.KParameter
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

fun find(record: Record, alias: String, it: KParameter): Any? {
    if (it.name == "id") return record[alias].asNode().id()
    val value = record[alias][it.name]
    return when (value.type()) {
        TYPE_SYSTEM.STRING() -> value.asString()
        TYPE_SYSTEM.NULL() -> null
        else -> value.asDouble()
    }
}

inline fun <reified T : Node> Session.match(node: T, alias: String = "n"): Set<T> {
    val label = T::class.annotations.firstOrNull { it is Label }?.let { it as Label }?.name ?: T::class.simpleName
    val query = "MATCH ($alias:$label { ${constraints(node)} }) RETURN $alias"
    return this.use { session ->
        session.run(query).list().mapNotNull { record ->
            val args = T::class.primaryConstructor?.parameters?.map {
                find(record, alias, it)
            } ?: emptyList()
            T::class.primaryConstructor?.call(*args.toTypedArray())
        }.toSet()
    }
}

fun <T : Node> constraints(entity: T) = entity.let {
    with(it::class) {
        memberProperties.mapNotNull { member ->
            if (member.getter.call(it) == null) null
            else when (member.returnType.classifier == String::class) {
                true -> "${member.name}: \"${member.getter.call(it)}\""
                false -> "${member.name}: ${member.getter.call(it)}"
            }
        }.joinToString(",")
    }
}
