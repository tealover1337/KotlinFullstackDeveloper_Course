package ru.mephi


class InvalidAddressException(message: String) : RuntimeException(message)
class InvalidTypeException(message: String) : RuntimeException(message)

val Transactions: MutableList<Pair<Int, String>> = mutableListOf()

fun isValid(address: String, predicate: Boolean): Boolean {
    if (predicate(address)) { return true }
    throw InvalidAddressException("You tried to perform a transaction with invalid address.")
}

fun predicate(address: String): Boolean {
    if (address != "debug") {return true} else {return false} // test mode
}

open class AnyTypeLogger<in T: OnlineTransaction> {
    open fun log(transaction: T) {
        if (transaction is BankTransaction) {BankTransactionLogger.log(transaction)}
        if (transaction is CryptoTransaction) {CryptoTransactionLogger.log(transaction)}
        if (transaction is PaypalTransaction) {PaypalTransactionLogger.log(transaction)}
    }
}
class ErrorTransactionLogger<in T: OnlineTransaction> {
    fun log(transaction: T) {
        println("Online transaction cancelled due to invalid address. Amount or code: ${transaction.component1()}, entered address: ${transaction.component2()}.")
    }
}

object BankTransactionLogger : AnyTypeLogger<BankTransaction>() {
    override fun log(transaction: BankTransaction) {
        println("Bank transaction happened. Transferred ${transaction.amount} U.E. to deposit #${transaction.address}.")
    }
}
object PaypalTransactionLogger : AnyTypeLogger<PaypalTransaction>() {
    override fun log(transaction: PaypalTransaction) {
        println("PayPal transaction happened. Transferred ${transaction.amount} U.E. to PayPal account with email: ${transaction.address}.")
    }
}
object CryptoTransactionLogger : AnyTypeLogger<CryptoTransaction>() {
    override fun log(transaction: CryptoTransaction) {
        println("Crypto transaction happened. Transferred ${transaction.decoded} U.E. to crypto wallet with id: ${transaction.address}.")
    }
}


