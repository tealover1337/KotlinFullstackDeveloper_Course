package ru.mephi

import kotlin.random.Random
import kotlin.math.min

// Used this to create a global variable
object Global {
    var buying = "none"
    var maxPossibleToBuy = 0
}
interface ConsoleWork {
    fun start()
}
object ConsoleWorkImpl : ConsoleWork {
    override fun start() {
        while (true) {
            println("-----x-----\nГлавное меню:\n1 - Проверить баланс\n2 - Совершить сделку\n3 - Выйти\n-----x-----")
            val received = readln()
            when (received) {
                "1" -> showBalance()
                "2" -> choosePair()
                "3" -> {
                    println("Спасибо за выбор нашего обменника! Всего вам доброго!")
                    break
                }
                else -> println("Похоже, вы ввели не цифру от 1 до 3. Попробуйте снова.")
            }
        }
    }
}

// Test implication
fun choosePair() {
    println("---\nДоступные валютые пары:")
    currencyPairsOutput()
    println("Выберите валютную пару в формате ВАЛЮТА_ВАЛЮТА, они указаны выше. Введите -1 для возвращения в меню.")
    while (true) {
        val pair = readln()
        if (pair.indexOf('_') != -1) {
            if (pair.split("_").count() == 2) {
                val cur1 = pair.split("_")[0]
                val cur2 = pair.split("_")[1]
                if (currencyCheck(cur1) && currencyCheck(cur2) && pairCheck(cur1, cur2)) {
                    pairWork(cur1, cur2)
                    break
                }
                else if (currencyCheck(cur1) && currencyCheck(cur2)) { wrongPair() }
                else { wrongFormat() }
            }
            else { wrongFormat() }
        }
        else if (pair == "-1") { break }
        else { wrongFormat() }
    }
    return
}
fun wrongFormat() {
    println("Неверный формат валютной пары и/или введены несуществующие валюты. Попробуйте еще раз.")
}
fun wrongPair() {
    println("Указана валютная пара, сделка по которой не предусмотрена. Попробуйте еще раз.")
}
fun isNumber(received: String): Boolean {
    try {
        received.toInt()
        return true
    }
    catch (e: NumberFormatException) {return false}
}
fun randomNumberGenerator(): Double {
    val generated = Random.nextDouble(10.toDouble()) - 5
    return (1 + generated / 100)
}

fun pairWork(cur1: String, cur2: String) {
    // Getting courses for each currency
    val course1 = Currencies.valueOf(cur1).course
    val course2 = Currencies.valueOf(cur2).course
    println("Текущий курс: 1$cur1 = ${(( (course1.toDouble() / course2.toDouble()) * 1000).toInt()).toDouble() / 1000}$cur2")
    // Getting currency we want to buy
    while (true) {
        println("Выберите валюту для покупки: $cur1 или $cur2. Введите -1 для возвращения в меню.")
        Global.buying = readln()
        if (Global.buying == "-1") { return }
        else if (!(Global.buying == cur1 || Global.buying == cur2)) { println("Вы ввели неверную валюту. Попробуйте еще раз.") }
        else { break }
    }
    if (Global.buying == cur1) {
        Global.maxPossibleToBuy = min(Bank.valueOf(cur1).balance, (course2.toDouble()/course1.toDouble() * User.valueOf(cur2).balance).toInt())
        println("Доступно к покупке: ${Global.maxPossibleToBuy.toDouble()/100} $cur1")
    }
    else {
        Global.maxPossibleToBuy = min(Bank.valueOf(cur2).balance, (course1.toDouble()/course2.toDouble() * User.valueOf(cur1).balance).toInt())
        println("Доступно к покупке: ${Global.maxPossibleToBuy.toDouble()/100} $cur2")
    }
    // Buy operation
    println("Введите, сколько ${Global.buying} вы хотите приобрести (введите -1 для возвращения в меню):")
    while (true) {
        val received = readln()
        if (received == "-1") { return }
        else if (isNumber(received)) {
            val amount = received.toInt()*100
            // If everything is fine
            if (amount > 0 && amount <= Global.maxPossibleToBuy) {
                if (Global.buying == cur1) {
                    User.valueOf(cur2).balance -= amount * (course1.toDouble() / course2.toDouble()) .toInt()
                    Bank.valueOf(cur2).balance += amount * (course1.toDouble() / course2.toDouble()) .toInt()
                    Bank.valueOf(cur1).balance -= amount
                    User.valueOf(cur1).balance += amount
                }
                else {
                    User.valueOf(cur1).balance -= amount * (course2.toDouble() / course1.toDouble()) .toInt()
                    Bank.valueOf(cur1).balance += amount * (course2.toDouble() / course1.toDouble()) .toInt()
                    Bank.valueOf(cur2).balance -= amount
                    User.valueOf(cur2).balance += amount
                }
                Currencies.valueOf(cur1).course = (Currencies.valueOf(cur1).course.toDouble() * randomNumberGenerator()).toInt()
                Currencies.valueOf(cur2).course = (Currencies.valueOf(cur2).course.toDouble() * randomNumberGenerator()).toInt()
                println("Успешная сделка! Поздравляем. Ваш новый баланс можно посмотреть через главное меню, новый курс - через меню сделок.")
                break
            }
            else { println("Вы ввели неправильное значение. Попробуйте снова.") }
        }
        else { println("Пожалуйста, попробуйте снова, но на этот раз введите число.") }
    }
    return
}

fun main(args: Array<String>) {
    ConsoleWorkImpl.start()
}