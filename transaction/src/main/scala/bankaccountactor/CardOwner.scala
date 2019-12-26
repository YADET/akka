package bankaccountactor

import akka.actor.{Actor, ActorRef}
import bankaccountactor.BankAccount.{Deposit, Statement, Withdraw}
import bankaccountactor.CardOwner.Account



object CardOwner{
  case class Account(actorref: ActorRef)
}

class CardOwner extends Actor{


  override def receive: Receive ={

    case Account(actorref) =>

      actorref ! Deposit(10000)
      actorref ! Withdraw(1000)
      actorref ! Statement
      actorref ! Withdraw(5000)

    case message => println(message)

  }
}
