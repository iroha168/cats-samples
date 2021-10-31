import cats._
import cats.implicits._

case class Account(id: Long, number: String, balance: Double, owner: String)

object Account {
  implicit val toStringShow: Show[Account] = Show.fromToString

  object Instances {
    implicit val byOwnerAndBalance: Show[Account] = Show.show { account =>
      s"${account.owner} - $$${account.balance}"
    }

    implicit val prettyByOwner: Show[Account] = Show.show { account =>
      s"this account belongs to ${account.owner}"
    }
  }

}

object ShowDemo extends App{
  val iroha = Account(1, "123-45", 2000, "iroha")
  println(Account.toStringShow.show(iroha))
  println(Account.Instances.byOwnerAndBalance.show(iroha))
  println(Account.Instances.prettyByOwner.show(iroha))
  print(iroha.show)
}
