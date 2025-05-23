

import java.util.*

fun main(args: Array<String>) {
    val reader = Scanner(System.`in`)
    print("Enter two numbers: ")
    val first = reader.nextDouble()
    val second = reader.nextDouble()

    print("Enter an operator (+, -, *, /): ")
    val operator = reader.next()[0]

    val result: Double = when (operator) {
        '+' -> first + second
        '-' -> first - second
        '*' -> first * second
        '/' -> first / second
        else -> {
            println("Error! operator is not correct")
            return
        }
    }

    println("$first $operator $second = $result")
}
