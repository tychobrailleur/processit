package com.weblogism.processit.client

import java.sql.Timestamp
import java.util.UUID

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.camel.{CamelExtension, CamelMessage, Consumer, Producer}
import akka.actor.actorRef2Scala
import com.google.gson.internal.LinkedTreeMap
import slick.jdbc.JdbcBackend.Database
import com.weblogism.processit.client.model.Tables
import slick.driver.PostgresDriver.api._
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.dataformat.JsonLibrary

/**
  * Client picks up a JSON message from the <code>test.incoming</code> queue, logs it, and sends
  * it to the processing queue, <code>rule.incoming</code>.
  *
  * <p>Cf. http://doc.akka.io/docs/akka/2.4.1/scala/camel.html for more details on akka camel.
  */
object Main {

  def main(args: Array[String]):Unit = {
    val system = ActorSystem("processit-client")
    CamelExtension(system).context.addRoutes(new AuditUnmarshaller(system))
    val rabbitProducer = system.actorOf(Props[RabbitProducer])
    val smallRouter = system.actorOf(Props[SmallRouter])
    val rabbitConsumer = system.actorOf(Props(classOf[RabbitConsumer], rabbitProducer, smallRouter))
    val auditHandler = system.actorOf(Props[AuditHandler])
  }


  class RabbitConsumer(producer: ActorRef, router: ActorRef) extends Consumer with Tables {
    // A consumer consumes messages from endpoint
    override def endpointUri = "rabbitmq://localhost/test.queue?queue=test.incoming&durable=true&autoDelete=false&exchangePattern=InOnly"

    override def receive = {
      case msg:CamelMessage =>  {
        router ! msg.bodyAs[String]
        producer ! msg.bodyAs[String]
      }
      case _ =>
    }
  }

  class SmallRouter extends Actor with Producer {
    override def oneway: Boolean = true
    override def endpointUri: String = "direct:auditProcessing"
  }

  class AuditUnmarshaller(system: ActorSystem) extends RouteBuilder with Tables {
    override def configure: Unit = {
      from("direct:auditProcessing").unmarshal().json(JsonLibrary.Gson).to("direct:audit")
    }
  }

  class AuditHandler extends Consumer with Tables {
    override def endpointUri = "direct:audit"

    override def receive = {
      case msg:CamelMessage => {
        val map:LinkedTreeMap[String,Object] = msg.bodyAs[LinkedTreeMap[String,Object]]
        val code:String = map.getOrDefault("code", "A001").asInstanceOf[String]
        val db = Database.forConfig("mydb")
        db.run(DBIO.seq(
          auditLogs += (UUID.randomUUID(), code, new Timestamp(new java.util.Date().getTime))
        ))
      }
    }
  }

  class RabbitProducer extends Actor with Producer {
    // InOnly exchange
    override def oneway: Boolean = true

    // Messages sent to the producer are sent to the endpointUri.
    override def endpointUri = "rabbitmq://localhost/rule.queue?queue=rule.incoming&durable=true&autoDelete=false&exchangePattern=InOnly"
  }
}
