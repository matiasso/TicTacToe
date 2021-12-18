package tictactoe


object Markers extends Enumeration {
  // These can maybe be used for the TicTacToe grid for easier readability
  type Marker = Value
  val Empty: Markers.Value = Value(0, "-")
  val X: Markers.Value = Value(1, "X")
  val O: Markers.Value = Value(2, "O")

  def isFree(marker: Marker) : Boolean = { marker == Empty }

}
