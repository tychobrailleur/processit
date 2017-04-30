package com.weblogism.processit.processor

import akka.actor.ActorSystem
import akka.actor.Props
import akka.camel.{CamelExtension, CamelMessage, Consumer, Producer}
import akka.actor.actorRef2Scala
import com.google.gson.internal.LinkedTreeMap
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.dataformat.JsonLibrary
import org.kie.api.KieServices
import org.kie.api.runtime.KieContainer
import org.kie.api.event.rule.DebugAgendaEventListener
import org.kie.api.event.rule.DebugRuleRuntimeEventListener

/**
  * Pick up message from <code>rule.incoming</code> queue, and process it with Drools.
  */
object Main {

  def main(args: Array[String]):Unit = {
    val system = ActorSystem("processit-rules")
    CamelExtension(system).context.addRoutes(new MessageUnmarshaller(system))
    val rabbitConsumer = system.actorOf(Props[RabbitConsumer])
  }

  class MessageUnmarshaller(system: ActorSystem) extends RouteBuilder {
    override def configure: Unit = {
      from("rabbitmq://localhost/rule.queue?queue=rule.incoming&durable=true&autoDelete=false&exchangePattern=InOnly")
        .unmarshal().json(JsonLibrary.Gson).to("direct:ruleProcessing")
    }
  }

  class RabbitConsumer extends Consumer with akka.actor.ActorLogging {
    val ks:KieServices = KieServices.Factory.get()
    val kc:KieContainer = ks.getKieClasspathContainer()
    def endpointUri = "direct:ruleProcessing"

    def receive = {
      case msg:CamelMessage => {

        val map:LinkedTreeMap[String,Object] = msg.bodyAs[LinkedTreeMap[String,Object]]
        val code:String = map.getOrDefault("code", "A001").asInstanceOf[String]
        val number:String = map.getOrDefault("number", null).asInstanceOf[String]
        val agentId:Double = map.getOrDefault("agent_id", null).asInstanceOf[Double]

        val ksession = kc.newKieSession("MessageProcessor")

        ksession.addEventListener( new DebugAgendaEventListener() )
        ksession.addEventListener( new DebugRuleRuntimeEventListener() )

        val m = new Message(code, number, agentId)

        ksession.insert(m)
        ksession.fireAllRules()
      }
    }
  }
}
