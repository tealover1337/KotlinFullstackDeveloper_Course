package kfd.cherkasov

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.util.concurrent.Executors

data class Person(
    val name: String,
    val age: Int,
    val isMale: Boolean,
    val test: List<Cocojambo>,
    val fuf: FUBAR
)
data class FUBAR(
    val zrNePo: Boolean,
    val foo: Int,
    val bar: String,
    val test: Cocojambo
)
data class Cocojambo(
    val one: Int,
    val two: String
)

fun main() {
    println("Введите желаемое количество переводов дата класса в json и обратно: ")
    val n = readln().toInt()

    val obj3 = Cocojambo(11, "12")
    val obj2 = FUBAR(false, 228, "o_O", obj3)
    val obj = Person("Ivan", 17, true, listOf(obj3, obj3), obj2)
    val threadPool = Executors.newFixedThreadPool(4)
    val mapper = jacksonObjectMapper()

    val startTime = System.nanoTime()
    try {
        repeat(n) {
            //threadPool.submit { deserialize(serialize(obj, threadPool), demon::class)}.get()
            deserialize(serialize(obj, threadPool), Person::class)
        }
    }
    finally {
        threadPool.shutdown()
    }
    val endTime = System.nanoTime()
    val time = (endTime - startTime) / 1000000
    println("Multi-thread serialization/deserialization time: $time ms, 1 operation: ${time.toDouble() / n} ms")

    val newStartTime = System.nanoTime()
    repeat(n) {
        var temp: Person = mapper.readValue(mapper.writeValueAsString(obj))
    }
    val newEndTime = System.nanoTime()
    val newtime = (newEndTime - newStartTime) / 1000000
    println("Jackson serialization/deserialization time: $newtime ms, 1 operation: ${newtime.toDouble() / n} ms")
}