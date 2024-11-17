package ru.mephi

import kotlin.math.PI

//массивы всех площадей и периметров (в тг ответили, что нужно вывести список с площадью/периметром каждой фигуры
var areas: MutableList<Double> = mutableListOf()
var perimeters: MutableList<Double> = mutableListOf()

class WrongOperationTypeException(message: String): Exception(message)
class BadPropertyException(message: String): Exception(message)

//классы фигур
open class Figure(val property: Double) {
    open val name: String = "ToBeEntered"
}

class Circle(property: Double) : Figure(property) {
    override val name = "Circle"
    init {
        println("$name(property = $property)")
    }
}

class Square(property: Double) : Figure(property) {
    override val name = "Square"
    init {
        println("$name(property = $property)")
    }
}
//рабочие классы
interface ConsoleService {
    fun work()
}

interface FigureService {
    fun addSquare(property: Double)
    fun addCircle(property: Double)
    fun getPerimeter()
    fun getArea()
}

object FigureServiceImpl : FigureService {
    override fun addSquare(property: Double) {
        propertyCheck(property)
        val square = Square(property)
        val new_area: Double = property * property
        val new_perimeter: Double = 4 * property
        areas.add(new_area)
        perimeters.add(new_perimeter)
    }
    override fun addCircle(property: Double) {
        propertyCheck(property)
        val circle = Circle(property)
        val new_area: Double = PI * property * property
        val new_perimeter: Double = 2 * PI * property
        areas.add(new_area)
        perimeters.add(new_perimeter)
    }
    override fun getArea() {
        println(areas)
    }

    override fun getPerimeter() {
        println(perimeters)
    }
}
//работа с консолью
object ConsoleServiceImpl : ConsoleService {
    override fun work() {
        while(true) {
            println("-----x-----")
            println("Введите тип операции, которую хотите исполнить:\nINSERT - добавить фигуру\nGET_AREA - получить площадь всех фигур\nGET_PERIMETER - получить периметр всех фигур\nEXIT - завершить выполнение")
            println("-----x-----")
            val received = readln()
            val operation = getOperation(received)
            when (operation) {
                1 -> addFigure()
                2 -> getArea()
                3 -> getPerimeter()
                4 -> break
                5 -> throw WrongOperationTypeException("Введен неизвестный тип операции: $received")
            }
        }
        println("Всего хорошего!")
    }
}
//работа с фигурами
fun getOperation(entered: String): Int {
    when (entered) {
        "INSERT", "Insert", "insert" -> return 1
        "GET_AREA", "Get_Area", "Get_area", "get_area" -> return 2
        "GER_PERIMETER", "Get_Perimeter", "Get_perimeter", "get_perimeter" -> return 3
        "EXIT", "Exit", "exit" -> return 4
        else -> return 5
    }
}

fun addFigure() {
    println("Введите property (property > 0!!!)")
    val property: Double = readln().toDouble()
    println("Введите тип (строго Circle или Square, exception не реализован!)")
    val type: String = readln()
    if (type == "Circle") {
        FigureServiceImpl.addCircle(property)
    }
    else if (type == "Square") {FigureServiceImpl.addSquare(property)}
}

fun getArea() {
    FigureServiceImpl.getArea()
}

fun getPerimeter() {
    FigureServiceImpl.getPerimeter()
}
//проверка на адекватное значение property, выброс Exception в FigureServiceImpl
fun propertyCheck(property: Double) {
    if (property.isNaN() || property <= 0) {
        throw BadPropertyException("Введено неверное значение параметра property: $property")
    }
}

fun main() {
    ConsoleServiceImpl.work()
}