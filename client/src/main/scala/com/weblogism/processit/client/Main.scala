package com.weblogism.processit.client

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.camel.CamelMessage
import akka.actor.actorRef2Scala
import akka.camel.Consumer
import akka.camel.Producer
import akka.camel.Ack

object Main {

  def main(args: Array[String]):Unit = {
    val system = ActorSystem("processit-client")
    val rabbitProducer = system.actorOf(Props[RabbitProducer])
    val rabbitConsumer = system.actorOf(Props(classOf[RabbitConsumer], rabbitProducer))
  }


  class RabbitConsumer(producer: ActorRef) extends Consumer {
    def endpointUri = "rabbitmq://localhost/test.queue?queue=test.incoming&durable=true&autoDelete=false&exchangePattern=InOnly"

    def receive = {
      case msg:CamelMessage =>  {
        Console.println(msg.bodyAs[String])
        producer ! msg.bodyAs[String]
      }
    }
  }

  class RabbitProducer extends Actor with Producer {
    override def oneway: Boolean = true
    override def endpointUri = "rabbitmq://localhost/rule.queue?queue=rule.incoming&durable=true&autoDelete=false&exchangePattern=InOnly"
  }
}
