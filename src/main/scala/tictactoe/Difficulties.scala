package tictactoe

object Difficulties extends Enumeration {
  type Difficulty = Value
  val Easy: Difficulties.Value = Value(0, "Easy")
  val Normal: Difficulties.Value = Value(1, "Normal")
  val Impossible: Difficulties.Value = Value(2, "Impossible")
}
