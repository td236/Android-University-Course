fun main(args: Array<String>) {
    println("Hello World")
}

fun dodaj (x: Int, y: Int) = x + y

fun funkcja(x: Int) : Int {
    return x*x
}

fun f() {
var zmienna: Int = 0
val stala: Int = 1

var nullable: String? = "Hello world"
var nonnullable: String = "Hello world"
nullable = null
//nonnullable = null
}

open class Pies {
    open fun szczekaj() {
        println("hau hau")
    }
}

class Owczarek : Pies() {
    override fun szczekaj() {
        println("Wuf Wuff")
    }
}

fun bezIfow(student: Any) {
    when(student) {
        12345 -> print("Indeks")
        "Jan Kowalski" -> print("po imieniu")
        is Double -> print("wzrost")
        else -> print("nie znam")
    }
}

fun petle(args: Array<String>) {
    for (i in 1..5 step 2) print(i)
}
