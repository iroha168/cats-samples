import cats._
import cats.implicits._

case class Speed(metersPerSecond: Double) {
  def kilometersPerSec: Double = metersPerSecond / 1000.0

  def milesPerSec: Double = metersPerSecond / 1609.5
}

object Speed {
  def addSpeed(s1: Speed, s2: Speed): Speed =
    Speed(s1.metersPerSecond + s2.metersPerSecond)

  //  implicit val monoidSpeed: Monoid[Speed] = new Monoid[Speed] {
  //    override def empty: Speed = Speed(0)
  //    override def combine(x: Speed, y: Speed): Speed = addSpeed(x, y)
  //  }
  implicit val eqSpeed: Eq[Speed] = Eq.fromUniversalEquals
  implicit val monoidSpeed: Monoid[Speed] = Monoid.instance(Speed(0), addSpeed)
}

object Monoids {
  //  implicit val sumMonoid: Monoid[Int] = new Monoid[Int]{
  //    override def empty: Int = 0
  //    override def combine(x: Int, y: Int): Int = x + y
  //  }
  val sumMonoid: Monoid[Int] = Monoid.instance(0, _ + _)

  //  implicit val minMonoid: Monoid[Int] = new Monoid[Int]{
  //    override def empty: Int = Int.MaxValue
  //
  //    override def combine(x: Int, y: Int): Int = Math.min(x, y)
  //  }
  val minMonoid: Monoid[Int] = Monoid.instance(Int.MaxValue, Math.min)

  def listMonoid[A]: Monoid[List[A]] = Monoid.instance(Nil, _ ++ _)

  val stringMonoid: Monoid[String] = Monoid.instance("", _ + _)
}

object MonoidDemo extends App {
  println(Monoid[Speed].combine(Speed(1000), Speed(2000)))
  println(Monoid.combine(Speed(1000), Speed(2000)))
  println(Speed(1100) |+| Speed(2200))
  println(Monoid[Speed].empty)
  println(Monoid.combine(Speed(1000), Monoid[Speed].empty))
  println(Monoid[Speed].combineAll(List(Speed(100), Speed(200), Speed(300))))
  println(List(Speed(100), Speed(200), Speed(300)).combineAll)
  println(Monoid[Speed].isEmpty(Speed(100)))
  println(Monoid[Speed].isEmpty(Speed(0)))
  println(Monoids.sumMonoid.combine(3, 4))
  println(Monoids.minMonoid.combine(6, 4))
  println(Monoids.minMonoid.combine(6, Monoids.minMonoid.empty))
  println(Monoids.listMonoid[Boolean].combine(List(true, false), List(false, true)))
  println(Monoids.listMonoid[Boolean].combine(List(true, false), List(false, true)))
}

