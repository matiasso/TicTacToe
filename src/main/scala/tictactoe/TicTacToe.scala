package tictactoe
import tictactoe.Markers.{Empty, Marker}

class TicTacToe(val player1: Player, val player2: Player) {
  // A 2D-array of the markers
  val grid: Array[Array[Marker]] = Array.fill[Array[Marker]](3)(Array.fill[Marker](3)(Markers.Empty))
  val player1Turn: Boolean = false   // This can be randomized later if wanted(?)

  def placeMarker(row: Short, column: Short, marker: Marker): Unit = {
    if (grid(row)(column) == Empty)
      grid(row)(column) = marker
  }
}
