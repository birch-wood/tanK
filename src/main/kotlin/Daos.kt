abstract class Node(val id: Long)

@Target(AnnotationTarget.CLASS)
annotation class Label(val value: String)

@Label("Recipe")
class Recipe(
    id:Long = -1,
    val name:String = "name"
) : Node(id)
