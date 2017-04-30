package com.weblogism.processit.model
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Accounts.schema ++ Customers.schema ++ TransactionLogs.schema ++ Transactions.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Accounts
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param number Database column number SqlType(varchar), Length(40,true)
   *  @param customerId Database column customer_id SqlType(int8), Default(None) */
  final case class AccountsRow(id: Long, number: String, customerId: Option[Long] = None)
  /** GetResult implicit for fetching AccountsRow objects using plain SQL queries */
  implicit def GetResultAccountsRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[Long]]): GR[AccountsRow] = GR{
    prs => import prs._
    AccountsRow.tupled((<<[Long], <<[String], <<?[Long]))
  }
  /** Table description of table accounts. Objects of this class serve as prototypes for rows in queries. */
  class Accounts(_tableTag: Tag) extends profile.api.Table[AccountsRow](_tableTag, "accounts") {
    def * = (id, number, customerId) <> (AccountsRow.tupled, AccountsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(number), customerId).shaped.<>({r=>import r._; _1.map(_=> AccountsRow.tupled((_1.get, _2.get, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column number SqlType(varchar), Length(40,true) */
    val number: Rep[String] = column[String]("number", O.Length(40,varying=true))
    /** Database column customer_id SqlType(int8), Default(None) */
    val customerId: Rep[Option[Long]] = column[Option[Long]]("customer_id", O.Default(None))

    /** Foreign key referencing Customers (database name accnt_cust_fk) */
    lazy val customersFk = foreignKey("accnt_cust_fk", customerId, Customers)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Accounts */
  lazy val Accounts = new TableQuery(tag => new Accounts(tag))

  /** Entity class storing rows of table Customers
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(varchar), Length(255,true) */
  final case class CustomersRow(id: Long, name: String)
  /** GetResult implicit for fetching CustomersRow objects using plain SQL queries */
  implicit def GetResultCustomersRow(implicit e0: GR[Long], e1: GR[String]): GR[CustomersRow] = GR{
    prs => import prs._
    CustomersRow.tupled((<<[Long], <<[String]))
  }
  /** Table description of table customers. Objects of this class serve as prototypes for rows in queries. */
  class Customers(_tableTag: Tag) extends profile.api.Table[CustomersRow](_tableTag, "customers") {
    def * = (id, name) <> (CustomersRow.tupled, CustomersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name)).shaped.<>({r=>import r._; _1.map(_=> CustomersRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(varchar), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
  }
  /** Collection-like TableQuery object for table Customers */
  lazy val Customers = new TableQuery(tag => new Customers(tag))

  /** Entity class storing rows of table TransactionLogs
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param payload Database column payload SqlType(text), Default(None)
   *  @param dateCreated Database column date_created SqlType(timestamp), Default(None) */
  final case class TransactionLogsRow(id: Long, payload: Option[String] = None, dateCreated: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching TransactionLogsRow objects using plain SQL queries */
  implicit def GetResultTransactionLogsRow(implicit e0: GR[Long], e1: GR[Option[String]], e2: GR[Option[java.sql.Timestamp]]): GR[TransactionLogsRow] = GR{
    prs => import prs._
    TransactionLogsRow.tupled((<<[Long], <<?[String], <<?[java.sql.Timestamp]))
  }
  /** Table description of table transaction_logs. Objects of this class serve as prototypes for rows in queries. */
  class TransactionLogs(_tableTag: Tag) extends profile.api.Table[TransactionLogsRow](_tableTag, "transaction_logs") {
    def * = (id, payload, dateCreated) <> (TransactionLogsRow.tupled, TransactionLogsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), payload, dateCreated).shaped.<>({r=>import r._; _1.map(_=> TransactionLogsRow.tupled((_1.get, _2, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column payload SqlType(text), Default(None) */
    val payload: Rep[Option[String]] = column[Option[String]]("payload", O.Default(None))
    /** Database column date_created SqlType(timestamp), Default(None) */
    val dateCreated: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("date_created", O.Default(None))
  }
  /** Collection-like TableQuery object for table TransactionLogs */
  lazy val TransactionLogs = new TableQuery(tag => new TransactionLogs(tag))

  /** Entity class storing rows of table Transactions
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param code Database column code SqlType(bpchar), Length(4,false), Default(None)
   *  @param dateCreated Database column date_created SqlType(timestamp), Default(None)
   *  @param accountId Database column account_id SqlType(int8), Default(None) */
  final case class TransactionsRow(id: Long, code: Option[String] = None, dateCreated: Option[java.sql.Timestamp] = None, accountId: Option[Long] = None)
  /** GetResult implicit for fetching TransactionsRow objects using plain SQL queries */
  implicit def GetResultTransactionsRow(implicit e0: GR[Long], e1: GR[Option[String]], e2: GR[Option[java.sql.Timestamp]], e3: GR[Option[Long]]): GR[TransactionsRow] = GR{
    prs => import prs._
    TransactionsRow.tupled((<<[Long], <<?[String], <<?[java.sql.Timestamp], <<?[Long]))
  }
  /** Table description of table transactions. Objects of this class serve as prototypes for rows in queries. */
  class Transactions(_tableTag: Tag) extends profile.api.Table[TransactionsRow](_tableTag, "transactions") {
    def * = (id, code, dateCreated, accountId) <> (TransactionsRow.tupled, TransactionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), code, dateCreated, accountId).shaped.<>({r=>import r._; _1.map(_=> TransactionsRow.tupled((_1.get, _2, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column code SqlType(bpchar), Length(4,false), Default(None) */
    val code: Rep[Option[String]] = column[Option[String]]("code", O.Length(4,varying=false), O.Default(None))
    /** Database column date_created SqlType(timestamp), Default(None) */
    val dateCreated: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("date_created", O.Default(None))
    /** Database column account_id SqlType(int8), Default(None) */
    val accountId: Rep[Option[Long]] = column[Option[Long]]("account_id", O.Default(None))

    /** Foreign key referencing Accounts (database name txn_accnt_fk) */
    lazy val accountsFk = foreignKey("txn_accnt_fk", accountId, Accounts)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Transactions */
  lazy val Transactions = new TableQuery(tag => new Transactions(tag))
}
