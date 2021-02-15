package code

object Demo extends App {
  import BusDrivers._

  println("Example 1:")

  println(
    format(
      solve(
        parse(
          """
          3 1 2 3
          3 2 3 1
          4 2 3 4 5
          """
        )
      )
    )
  )

  println("Example 2:")

  println(
    format(
      solve(
        parse(
          """
          2 1 2
          5 2 8
          """
        )
      )
    )
  )

  println("Example 3:")

  println(
    format(
      solve(
        parse(
          """
          1
          1
          1
          """
        )
      )
    )
  )
}

case class BusDriver(route: List[Int], gossips: Set[Int])

object BusDrivers {
  def parse(input: String): List[BusDriver] =
    input.trim
      .split("\n")
      .zipWithIndex
      .map { case (line, index) =>
        BusDriver(
          line.trim.split(" ").map(_.toInt).toList,
          Set(index)
        )
      }
      .toList

  def format(result: Option[Int]): String =
    result.fold("never")(n => (n + 1).toString)

  def solve(drivers: List[BusDriver], minute: Int = 0): Option[Int] = {
    if (dayIsOver(minute)) {
      None
    } else {
      val newDrivers = exchangeGossips(drivers, minute)

      if (everyoneKnowsEverything(newDrivers)) {
        Some(minute)
      } else {
        solve(newDrivers, minute + 1)
      }
    }
  }

  def dayIsOver(minute: Int): Boolean =
    minute >= 480

  def everyoneKnowsEverything(drivers: List[BusDriver]): Boolean =
    drivers.forall(_.gossips.size == drivers.length)

  def exchangeGossips(drivers: List[BusDriver], minute: Int): List[BusDriver] =
    drivers.map { x =>
      val newGossips: Set[Int] =
        drivers
          .filter(y => stop(x, minute) == stop(y, minute))
          .foldLeft(Set.empty[Int])(_ ++ _.gossips)

      x.copy(gossips = newGossips)
    }

  def stop(driver: BusDriver, minute: Int): Int =
    driver.route(minute % driver.route.length)
}
