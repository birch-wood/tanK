fun main() {
    val recipes = match<Recipe> {
        (name eq "Cookies")
    }.map { it.id }
    println(recipes)
}

