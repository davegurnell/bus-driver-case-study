package code

import org.scalatest._

class BusDriverSpec extends WordSpec with Matchers {
  import BusDrivers._

  "parse" should {
    "parse an example problem" in {
      val actual =
        parse(
          """
        3 1 2 3
        3 2 3 1
        4 2 3 4 5
        """
        )

      val expected =
        List(
          BusDriver(List(3, 1, 2, 3), Set(0)),
          BusDriver(List(3, 2, 3, 1), Set(1)),
          BusDriver(List(4, 2, 3, 4, 5), Set(2))
        )

      actual shouldBe expected
    }
  }

  "exchangeGossips" should {
    "exchange all gossips at a particular time point (time 0)" in {
      val initial = List(
        BusDriver(List(3, 1, 2, 3), Set(0)),
        BusDriver(List(3, 2, 3, 1), Set(1)),
        BusDriver(List(4, 2, 3, 4, 5), Set(2))
      )

      val actual = exchangeGossips(initial, 0)

      val expected =
        List(
          BusDriver(List(3, 1, 2, 3), Set(0, 1)),
          BusDriver(List(3, 2, 3, 1), Set(0, 1)),
          BusDriver(List(4, 2, 3, 4, 5), Set(2))
        )
    }

    "exchange all gossips at a particular time point (time 2)" in {
      val initial = List(
        BusDriver(List(3, 1, 2, 3), Set(0)),
        BusDriver(List(3, 2, 3, 1), Set(1)),
        BusDriver(List(4, 2, 3, 4, 5), Set(2))
      )

      val actual = exchangeGossips(initial, 2)

      val expected =
        List(
          BusDriver(List(3, 1, 2, 3), Set(0)),
          BusDriver(List(3, 2, 3, 1), Set(1, 2)),
          BusDriver(List(4, 2, 3, 4, 5), Set(1, 2))
        )
    }
  }

  "solve" should {
    "solve a converging problem" in {
      val initial = List(
        BusDriver(List(3, 1, 2, 3), Set(0)),
        BusDriver(List(3, 2, 3, 1), Set(1)),
        BusDriver(List(4, 2, 3, 4, 5), Set(2))
      )

      val actual = solve(initial)

      val expected =
        Some(4)

      actual shouldBe expected
    }

    "give up gracefully on a non-converging problem" in {
      val initial = List(
        BusDriver(List(2, 1, 2), Set(0)),
        BusDriver(List(5, 2, 8), Set(1))
      )

      val actual = solve(initial)

      val expected =
        None

      actual shouldBe expected
    }
  }

  "format" should {
    "format a converging result" in {
      format(Some(2)) shouldBe "3"
    }

    "format a non-converging result" in {
      format(None) shouldBe "never"
    }
  }

}
