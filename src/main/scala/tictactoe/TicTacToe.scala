package tictactoe
import tictactoe.Markers._

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class TicTacToe(val player1: Player, val player2: Player) {
  // A 2D-array of the markers
  val grid: Array[Array[Marker]] = Array.fill[Array[Marker]](3)(Array.fill[Marker](3)(Markers.Empty))
  var player1Turn: Boolean = true   // This can be randomized later if wanted(?)

  /**
   * Places the given marker to desired location
   * @param row number of the row (0, 1, or 2)
   * @param column number of the column (0, 1, or 2)
   * @param marker the marker which can be either X or O
   * @return Boolean indicating if the action was successful
   */
  def PlaceMarker(row: Short, column: Short, marker: Marker): Boolean = {
    assert(row >= 0 && row < 3)
    assert(column >= 0 && column < 3)
    // Check that the spot is empty
    if (grid(row)(column) == Empty) {
      // Place the marker
      grid(row)(column) = marker
      true
    } else {
      false
    }
  }

  /**
   * Returns the player whose turn it is
   * @return Player
   */
  def GetCurrentPlayer: Player = if (player1Turn) player1 else player2

  /**
   * Get the current marker, Player1 is always X and Player2 is O
   * @return Marker object
   */
  def GetCurrentMarker: Marker = GetCurrentPlayer.marker

  /**
   * Changes the turn from player1 to player2 or vice versa
   */
  def SwapTurn(): Unit = player1Turn = !player1Turn

  /**
   * Checks whether there are any Empty slots in the grid
   * @return Boolean
   */
  def BoardFull: Boolean = grid.forall(row => !row.contains(Empty))

  /**
   * Get the winner of the game (or None if the game is tied or not finished)
   * @return Option[Player]
   */
  def GetWinner: Option[Player] = {
    // Check for horizontal victories:
    for (r <- grid.indices) {
      // Are all the elements equal to the first rows element?
      if (grid(r).forall(m => m != Empty && m == grid(r)(0))) {
        // Return the player whose marker it is
        return if (player1.marker == grid(r)(0)) Some(player1) else Some(player2)
      }
    }
    // Check for vertical victories:
    for (c <- grid.indices) {
      // Are all the elements equal to the first columns element?
      if (grid.indices.forall(i => grid(i)(c) != Empty && grid(i)(c) == grid(0)(c))) {
        // Return the player whose marker it is
        return if (player1.marker == grid(0)(c)) Some(player1) else Some(player2)
      }
    }
    // Check for diagonal victories:
    if (grid.indices.forall(i => grid(i)(i) != Empty && grid(0)(0) == grid(i)(i))) {
      return if (player1.marker == grid(0)(0)) Some(player1) else Some(player2)
    }
    if (grid.indices.forall(i => grid(i)(2-i) != Empty && grid(0)(2) == grid(i)(2-i))) {
      return if (player1.marker == grid(0)(2)) Some(player1) else Some(player2)
    }
    None
  }

  /**
   * Returns a random (row, column) pair that is still empty
   * @return (Short, Short)
   */
  def RandomFreeSpot: (Short, Short) = {
    assert(!BoardFull)
    val rnd = new Random()
    var row = rnd.nextInt(3)
    var col = rnd.nextInt(3)
    // Optimize this in case of bad luck
    while (grid(row)(col) != Empty) {
      row = rnd.nextInt(3)
      col = rnd.nextInt(3)
    }
    (row.toShort, col.toShort)
  }

  /**
   * Get a textual representation of the board situation
   * @return String
   */
  def StringRepresentation: String = {
    val buff = new ArrayBuffer[String]
    for (row <- grid) {
      for (marker <- row) {
        buff += marker.toString + " "
      }
      buff += "\n"
    }
    buff.mkString("")
  }
}
