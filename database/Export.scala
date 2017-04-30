object Main extends App {
  def slickDriver = "slick.jdbc.PostgresProfile"
  def pkg = "com.weblogism.processit.model"
  def outputFolder = "/home/sebastien/dev/processit/database/src-gen"
  def url = "jdbc:postgresql://localhost/processit"
  def jdbcDriver = "org.postgresql.Driver"


  slick.codegen.SourceCodeGenerator.main(
    Array(slickDriver, jdbcDriver, url, outputFolder, pkg, "sebastien", "sebastien")
  )
}
