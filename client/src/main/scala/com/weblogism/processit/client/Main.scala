package com.weblogism.processit.client

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.camel.CamelMessage
import akka.actor.actorRef2Scala
import akka.camel.Consumer
import akka.camel.Producer

object Main {

  def main(args: Array[String]):Unit = {
    val system = ActorSystem("processit-client")
    val rabbitConsumer = system.actorOf(Props[RabbitConsumer])
  }


  class RabbitConsumer extends Consumer {
    def endpointUri = "rabbitmq://localhost/test.queue?queue=test.incoming&durable=true&autoDelete=false"

    def receive = {
      case msg =>  Console.println("Hello")
    }
  }
}
