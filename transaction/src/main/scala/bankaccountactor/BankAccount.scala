package bankaccountactor

import akka.actor.Actor
import bankaccountactor.BankAccount.{Deposit, Statement, TransferFailure, TransferSucceed, Withdraw}


object BankAccount{
  case class Deposit(amount:Int)
  case class Withdraw(amount:Int)
  case class TransferFailure(input:String)
  case class TransferSucceed(input:String)
  case object Statement
}

class BankAccount extends Actor{
  var money=0

  override def receive: Receive = {

    case Deposit(amount) =>
      if (amount>0) {
        money += amount
        sender() ! TransferSucceed(s"your money is ${money}")
      }
      else{
        sender() ! TransferFailure("Deposit cant be done")
      }

    case Withdraw(amount) =>
      if (amount < 0 || money < amount ) sender() ! TransferFailure("cant be done")
      else {
        money -= amount
        sender() ! TransferSucceed(s" your balance is ${money}")
      }

    case Statement => sender() ! s"you balance is ${money}"

  }
}
