package ru.mephi
object Console {
    fun work() {
        print("Enter amount of transactions you wish to proceed: ")
        val amount = readln().toInt()
        repeat(amount) {
            println("Enter transaction type: Bank, PayPal or Crypto:")
            val type = readln()
            println("Enter amount of U.E. you want to transfer (code if the type is Crypto), then enter the address:")
            val first = when (type) {
                "Bank" -> BankTransaction(readln().toInt(), readln())
                "PayPal" -> PaypalTransaction(readln().toInt(), readln())
                "Crypto", "HMSTR$" -> CryptoTransaction(readln().toInt(), readln())
                else -> throw InvalidTypeException("Wrong type entered: $type.")
            }
            val address = first.address
            try {
                if (isValid(address, predicate(address))) {
                    AnyTypeLogger<OnlineTransaction>().log(first)
                    Transactions.add(Pair(first.amount, first.address))
                }
            } catch (e: InvalidAddressException) {
                ErrorTransactionLogger<OnlineTransaction>().log(first)
            }
        }
        repeat(Transactions.size) {print("${Transactions[it]} ")}
    }
}
fun main() {
    Console.work()
}