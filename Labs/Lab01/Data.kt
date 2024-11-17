package ru.mephi

// To add a new pair add a line "NAME1_NAME2,".    !! don't forget the comma !!
enum class CurrencyPairs {
    RUB_EUR,
    RUB_USD,
    USD_EUR,
    USD_USDT,
    USD_BTC,
}

// All values in neutral points; add a new currency: Ctrl + C -> Ctrl + V and change name and course.
// Added code field to increase the readability (as for me) at DataWork.kt functions
enum class Currencies(var course: Int, val code: String) {
    RUB(100,"RUB"),
    USD(9525,"USD"),
    EUR(10507, "EUR"),
    USDT(9524, "USDT"),
    BTC(585294966, "BTC")
}

// Absolutely enormous decision to make User and Bank enums, but this is the only way I could use valueOf() to print balances not to use N -> inf println()'s
// The real balance is BalanceHere * .01
internal enum class User(var balance: Int) {
    RUB(100000000),
    USD(0),
    EUR(0),
    USDT(0),
    BTC(0)
}
enum class Bank(var balance: Int) {
    RUB(1000000),
    USD(100000),
    EUR(100000),
    USDT(100000),
    BTC(1000)
}
