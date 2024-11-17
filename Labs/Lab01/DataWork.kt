package ru.mephi

fun showBalance() {
    println("---\nБаланс:")
    enumValues<Currencies>().forEach { println("${User.valueOf(it.code).balance.toDouble() / 100} ${it.code}") }
}

fun currencyPairsOutput() {
    enumValues<CurrencyPairs>().forEach { println(it) }
}

// If the currency isn't added at Data file it will cause the enum IllegalArgumentException
fun currencyCheck(name: String): Boolean {
    return try { (Currencies.valueOf(name) in enumValues<Currencies>())}
    catch (e: IllegalArgumentException) {false}
}

// If the currency pair isn't real or is saved in enum in reversed variant the IllegalArgumentException will be thrown
// So we must catch it EVEN in situation with two real currencies that CAN be traded in pair
fun pairCheck(cur1: String, cur2: String): Boolean {
    try {
        val foo = "${cur1}_${cur2}"
        return (CurrencyPairs.valueOf(foo) in enumValues<CurrencyPairs>())
    }
    catch (e: java.lang.IllegalArgumentException) {
        try {
            val bar = "${cur2}_${cur1}"
            return (CurrencyPairs.valueOf(bar) in enumValues<CurrencyPairs>())
        }
        catch (e: java.lang.IllegalArgumentException) {return false}
    }
}

