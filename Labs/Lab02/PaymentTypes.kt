package ru.mephi

open class OnlineTransaction(val amount: Int, val address: String) {
    // open fun proceed() {}
    operator fun component1() = amount
    operator fun component2() = address
}
class BankTransaction : OnlineTransaction {
    constructor(amount: Int, address: String) : super(amount, address) // address is card number
}
class PaypalTransaction : OnlineTransaction {
    constructor(amount: Int, address: String) : super(amount, address) // address is email
}
class CryptoTransaction : OnlineTransaction {
    constructor(amount: Int, address: String) : super(amount, address) // address is wallet hash, amount is code
    val decoded = amount / 13 - 37
}

/*open class OfflineTransaction(val amount: Int, val address: String, val confirmation: Boolean) {
    val checkedFlag = false
    open fun checkAndProceed() {
        // bla bla bla
    }
}*/