package bankaccountactor

import akka.actor.{ActorSystem, Props}
import bankaccountactor.CardOwner.Account


object Actorrunner extends App {

  val system=ActorSystem("second")

  val bank=system.actorOf(Props[BankAccount],"bank")
  val cardholder=system.actorOf(Props[CardOwner],"cardholder")

  cardholder ! Account(bank)


}
