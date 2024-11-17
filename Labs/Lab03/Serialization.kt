package kfd.cherkasov

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.reflect.full.memberProperties

fun <T: Any> serialize(data: T, threadPool: ExecutorService): String {
    val string = StringBuilder()
    string.append("{\n")
    //threadPool.submit {string.appendData(data, 1, threadPool)}.get()
    string.appendData(data, 1, threadPool)
    string.append("}")
    return string.toString()
}

fun <T: Any> StringBuilder.appendData(data: T, tabs: Int, threadPool: ExecutorService) {
    val properties = data::class.memberProperties
        .map {property -> property.name}
        .sorted()
    val futures = mutableListOf<Future<Unit>>()
    properties.forEach { property ->
        var value = data::class.memberProperties
            .find {it.name == property}
            ?.call(data)
        if (value is String) { value = "\"" + value + "\""}
        repeat(tabs) {append("    ")}
        if (value!!::class.isData) {
            append("\"$property\": {\n")
            //threadPool.submit {appendData(value, tabs + 1, threadPool)}.get()
            appendData(value, tabs + 1, threadPool)
            repeat(tabs) {append("    ")}
            append("},\n")
        }
        else {
            append("\"$property\": $value,\n")
        }
    }
    // you are guaranteed with ,\n stringbuilder end so we just remove them both and add \n to get rid of last commas
    setLength(this.length - 2)
    append("\n")
}

