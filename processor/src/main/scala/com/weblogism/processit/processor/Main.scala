package com.weblogism.processit.processor

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.camel.CamelMessage
import akka.actor.actorRef2Scala
import akka.camel.Consumer
import akka.camel.Producer


import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import org.kie.api.event.rule.DebugAgendaEventListener
import org.kie.api.event.rule.DebugRuleRuntimeEventListener


object Main {

  def main(args: Array[String]):Unit = {
    val system = ActorSystem("processit-rules")
    val rabbitConsumer = system.actorOf(Props[RabbitConsumer])
  }

  class RabbitConsumer extends Consumer with akka.actor.ActorLogging {
    val ks:KieServices = KieServices.Factory.get()
    val kc:KieContainer = ks.getKieClasspathContainer()
    def endpointUri = "rabbitmq://localhost/test.queue?queue=test.incoming&durable=true&autoDelete=false"

    def receive = {
      case msg:CamelMessage => {
        val ksession = kc.newKieSession("MessageProcessor")

        ksession.addEventListener( new DebugAgendaEventListener() )
	    ksession.addEventListener( new DebugRuleRuntimeEventListener() )

        val m = new Message(msg.bodyAs[String], "12345", "132345")

		ksession.insert(m)
		ksession.fireAllRules()
      }
    }
  }
}
