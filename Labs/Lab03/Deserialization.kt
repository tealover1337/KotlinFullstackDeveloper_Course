package kfd.cherkasov


import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor


//THERE MUST BE NO DATA CLASSES INSIDE ARRAY, SORRY
fun <T: Any> deserialize(json: String, link: KClass<T>): Any {
    val splitJson = json.removeSurrounding("{\n","\n}").split("\n")
    val lines: MutableList<String> = mutableListOf()
    for (line in splitJson) {
        if ("    " + line.trim() == line && line.trim() != "},") {lines.add(line)}
        else {lines[lines.size - 1] += line}
    }
    val propertiesMap: MutableMap<String, Any?> = mutableMapOf()
    lines.forEach { line ->
        val (key, value) = line.removeSuffix(",").trim().split(":", limit = 2)
        val clearKey = key.removeSurrounding("\"")
        propertiesMap[clearKey] = parseValues(clearKey, value, link, json)
    }
    val dataClassConstructor = link.primaryConstructor
    return dataClassConstructor!!.callBy(dataClassConstructor.parameters.associateWith { parameter -> propertiesMap[parameter.name]})
}

fun <T: Any> parseValues(key: String, value: String, link: KClass<T>, json: String): Any? {
    val coolValue = value.removeSurrounding("\"")
        .trim()
    return when {
        // we don't use clean value to null check because it can be either null or sting "null" but it'll look the same
        value.trim() == "null" -> null
        value.trim() == "false" || value.trim() == "true" -> value.toBoolean()
        coolValue.toIntOrNull() != null -> coolValue.toInt()
        coolValue.toDoubleOrNull() != null -> coolValue.toDouble()
        coolValue.startsWith("[") && coolValue.endsWith("]") -> {
            val mass = coolValue.removeSurrounding("[","]")
                .split(",")
                .map { parseValues(key, it.trim(), link, json) }
            mass
        }
        coolValue.startsWith("{") && coolValue.endsWith("}") -> {
            val propertyClassifier = link.memberProperties.find { it.name == key }?.returnType?.classifier
            val propertyType = propertyClassifier as KClass<*>
            deserialize(backToJSON(json, key), propertyType)
        }
        else -> coolValue
    }
}

fun backToJSON(json: String, key: String): String {
    val string = StringBuilder()
    string.append("{\n")
    val jsonLines = json.removeSurrounding("{","}")
        .trim()
        .split("\n")
    var firstIndex = -1
    var lastIndex = -1
    jsonLines.forEach { line ->
        if (line.findAnyOf(listOf("\"" + key + "\"")) != null ) {
            firstIndex = jsonLines.indexOf(line) + 1
        } else if (firstIndex != -1) {
            if (line.startsWith("    }")) {lastIndex = jsonLines.indexOf(line) - 1}
        }
    }
    for (i in firstIndex..lastIndex) {string.append("${jsonLines[i].replaceFirst("    ", "")}\n")}
    string.append("}")
    return string.toString()
}
