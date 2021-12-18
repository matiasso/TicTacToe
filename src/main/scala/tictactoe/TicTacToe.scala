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
   * Gets the owner of given marker (X or O)
   * @param marker whose owner we want to find
   * @return Player
   */
  def GetMarkerOwner(marker: Marker): Option[Player] = {
    if (marker == Empty)
      return None
    if (player1.marker == marker) Some(player1) else Some(player2)
  }

  /**
   * Get the winner of the game (or None if the game is tied or not finished)
   * @return Option[Player]
   */
  def GetWinner: Option[Player] = {
    for (i <- grid.indices) {
      // Check for horizontal victories:
      // Are all the elements equal to the rows first element?
      if (grid(i)(0) != Empty && grid(i).forall(marker => marker == grid(i)(0))) {
        // Return the player whose marker it is
        return GetMarkerOwner(grid(i)(0))
      }
      // Check for vertical victories:
      // Are all the elements equal to the first columns element?
      if (grid(0)(i) != Empty && grid.indices.forall(j => grid(j)(i) == grid(0)(i))) {
        // Return the player whose marker it is
        return GetMarkerOwner(grid(0)(i))
      }
    }
    // Check for diagonal victories:
    if (grid(0)(0) != Empty && grid.indices.forall(i => grid(i)(i) == grid(0)(0))) {
      return GetMarkerOwner(grid(0)(0))
    }
    if (grid(0)(2) != Empty && grid.indices.forall(i => grid(i)(2 - i) == grid(0)(2))) {
      return GetMarkerOwner(grid(0)(2))
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
