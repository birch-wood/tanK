package net.emdal.tank.clause

class MergeClause(override var query: List<String> = listOf("MERGE ")) : GraphClause
