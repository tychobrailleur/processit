package com.weblogism.processit.client.model

import java.sql.Timestamp
import java.util.UUID

import slick.driver.PostgresDriver.api._
import slick.lifted.ProvenShape

trait Tables {

  class AuditLogs(tag: Tag) extends Table[(java.util.UUID, String, String, Double, Timestamp)](tag, "audit_log") {
    def id: Rep[java.util.UUID] = column[java.util.UUID]("id", O.PrimaryKey)
    def code: Rep[String] = column[String]("code")
    def number: Rep[String] = column[String]("number")
    def agentId: Rep[Double] = column[Double]("agent_id")
    def dateCreated: Rep[Timestamp] = column[Timestamp]("date_created")

    def * : ProvenShape[(UUID, String, String, Double, Timestamp)] = (id, code, number, agentId, dateCreated)
  }

  lazy val auditLogs = TableQuery[AuditLogs]
}
