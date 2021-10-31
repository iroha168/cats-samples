//import cats._
//import cats.implicits._
//
//case class Account(id: Long, number: String, balance: Double, owner: String)
//
//object Account {
//  implicit val universalEq: Eq[Account] = Eq.fromUniversalEquals
//
//  object Instances {
//    //    implicit val byIdEq: Eq[Account] = Eq.instance[Account]((a1, a2) => a1.id == a2.id)
//    //    implicit val byIdEq: Eq[Account] = Eq.instance[Account]((a1, a2) => Eq[Long].eqv(a1.id, a2.id))
//    implicit def byIdEq(implicit eqLong: Eq[Long]): Eq[Account] = Eq.instance[Account]((a1, a2) => eqLong.eqv(a1.id, a2.id))
//
//    implicit def byIdEq2(implicit eqLong: Eq[Long]): Eq[Account] = Eq.by(_.id)
//
//    implicit def byNumEq(implicit eqString: Eq[String]): Eq[Account] = Eq.by(_.number)
//  }
//
//}
//
//object EqDemo extends App {
//  val account1 = Account(1, "123-56", 1000, "iroha")
//  val account2 Account(2, "123-56", 1500, "iroha")
//  println(Eq[Account].eqv(account1, account2))
//  //  import Account.Instances.byIdEq
//
//  import Account.Instances.byNumEq
//
//  println(Eq[Account].eqv(account1, account2))
//  println(account1 === account2)
//}
