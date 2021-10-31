import cats._
import cats.implicits._
case class AccountOrder(id: Long, number: String, balance: Double, owner: String)

object AccountOrder {
//  implicit val orderById: Order[AccountOrder] = Order.from((a1, a2) => Order[Long].compare(a1.id, a2.id))
  implicit def orderById(implicit ordering: Order[Long]): Order[AccountOrder] = Order.from((a1, a2) => ordering.compare(a1.id, a2.id))
  object Instances {
    implicit val orderByNumber: Order[AccountOrder] = Order.by(_.number)
    implicit val orderByBalance: Order[AccountOrder] = Order.by(_.balance)
  }
}

object OrderDemo extends App {
  def sort[A](list: List[A])(implicit orderA: Order[A]) = {
    list.sorted(orderA.toOrdering)
  }
  val account1 = AccountOrder(1, "442-21", 3000, "Julia")
  val account2 = AccountOrder(2, "442-21", 2500, "Romeo")
  implicit val orderByIdDesc: Order[AccountOrder] = Order.reverse(AccountOrder.orderById)
  println(sort[AccountOrder](List(account1, account2)))
  println(account1 compare account2)
  println(account1 min account2)
  println(account1 max account2)

}
