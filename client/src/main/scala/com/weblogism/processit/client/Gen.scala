package com.weblogism.processit.client

object Gen extends App {
    slick.codegen.SourceCodeGenerator.main(
      Array("slick.driver.PostgresDriver", "org.postgresql.Driver" , "jdbc:postgresql://localhost/fams", "/tmp", "com.weblogism.processit.client.model")
    )
}
