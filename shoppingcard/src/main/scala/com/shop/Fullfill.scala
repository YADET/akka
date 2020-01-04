package com.shop

import akka.actor.{Actor, ActorLogging}
import com.shop.Checkout.{Confirmed, Dispatchorder}

class Fullfill extends Actor with ActorLogging {

  var ordercount=45
  override def receive: Receive = {

    case Dispatchorder(item)=>
      ordercount+=1
      log.info(s"order $ordercount for item $item ordered.")
      sender() ! Confirmed

  }

}
