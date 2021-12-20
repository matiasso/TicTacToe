package tictactoe

import tictactoe.Difficulties._
import tictactoe.Markers._

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class TicTacToe(val soloMode: Boolean, val difficulty: Difficulty = Normal) {

  // Auxiliary constructors
  def this() = this(false)

  // Create a fresh-copy of the game with current settings
  def this(game: TicTacToe) = this(game.soloMode, game.difficulty)

  // Define players
  val player1 = new Player("Player 1", false, X)
  val player2 = new Player("Player 2", soloMode, O)

  // A 2D-array of the markers
  private val grid: Array[Array[Marker]] = Array.fill[Array[Marker]](3)(Array.fill[Marker](3)(Markers.Empty))
  private var player1Turn: Boolean = true // This can be randomized later if needed

  /**
   * Places the given marker to desired location
   *
   * @param row    number of the row (0, 1, or 2)
   * @param column number of the column (0, 1, or 2)
   * @param marker the marker which can be either X or O
   * @return Boolean indicating if the action was successful
   */
  def PlaceMarker(row: Int, column: Int, marker: Marker): Boolean = {
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
   * Places given marker to given index
   *
   * @param index  of the square (0-8)
   * @param marker the marker which can be either X or O
   * @return Boolean indicating if the action was successful
   */
  def PlaceMarker(index: Int, marker: Marker): Boolean = this.PlaceMarker(index / 3, index % 3, marker)

  /**
   * Get the marker object at given location
   *
   * @param row    number of the row (0, 1, or 2)
   * @param column number of the column (0, 1, or 2)
   * @return Marker at given location
   */
  def GetMarker(row: Int, column: Int): Marker = {
    assert(row >= 0 && row < 3)
    assert(column >= 0 && column < 3)
    grid(row)(column)
  }

  /**
   * Get the marker object at given index
   *
   * @param index of the item (0-8)
   * @return Marker at given index
   */
  def GetMarker(index: Int): Marker = this.GetMarker(index / 3, index % 3)

  /**
   * Returns the player whose turn it is
   *
   * @return Player
   */
  def GetCurrentPlayer: Player = if (player1Turn) player1 else player2

  /**
   * Get the current marker, Player1 is always X and Player2 is O
   *
   * @return Marker object
   */
  def GetCurrentMarker: Marker = GetCurrentPlayer.marker

  /**
   * Changes the turn from player1 to player2 or vice versa
   */
  def SwapTurn(): Unit = player1Turn = !player1Turn

  /**
   * Executes the AI move based on the difficulty of this instance
   *
   * @return Int index where the AI placed its marker
   */
  def ExecuteAiMove(): Int = {
    val index = difficulty match {
      case Easy => RandomFreeSpot
      case Normal => RandomFreeSpot
      case Impossible => RandomFreeSpot
      case _ => throw new Exception("Unknown difficulty! Could not execute AI move")
    }
    PlaceMarker(index, player2.marker)
    index
  }

  def minimax(depth: Int, board: Array[Marker]): Unit = {

  }

  /**
   * Checks whether there are any Empty slots in the grid
   *
   * @return Boolean
   */
  def BoardFull(board: Array[Array[Marker]] = grid): Boolean = board.forall(row => !row.contains(Empty))

  /**
   * Checks whether the game has ended
   *
   * @return Boolean
   */
  def GameEnded: Boolean = BoardFull() || GetWinner().isDefined

  /**
   * Gets the owner of given marker (X or O)
   *
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
   *
   * @return Option[Player]
   */
  def GetWinner(board: Array[Array[Marker]] = grid): Option[Player] = {
    for (i <- board.indices) {
      // Check for horizontal victories:
      // Are all the elements equal to the rows first element?
      if (board(i)(0) != Empty && board(i).forall(marker => marker == board(i)(0))) {
        // Return the player whose marker it is
        return GetMarkerOwner(board(i)(0))
      }
      // Check for vertical victories:
      // Are all the elements equal to the first columns element?
      if (board(0)(i) != Empty && board.indices.forall(j => board(j)(i) == board(0)(i))) {
        // Return the player whose marker it is
        return GetMarkerOwner(board(0)(i))
      }
    }
    // Check for diagonal victories:
    if (board(0)(0) != Empty && board.indices.forall(i => board(i)(i) == board(0)(0))) {
      return GetMarkerOwner(board(0)(0))
    }
    if (board(0)(2) != Empty && board.indices.forall(i => board(i)(2 - i) == board(0)(2))) {
      return GetMarkerOwner(board(0)(2))
    }
    None
  }

  /**
   * Returns a random index (0-8) that is still empty
   *
   * @return Int
   */
  def RandomFreeSpot: Int = {
    assert(!BoardFull())
    // Find all empty slots
    val emptySlots = grid.flatten.zipWithIndex.filter(_._1 == Empty).map(_._2)
    // Randomly select one of the emptySlots
    val rnd = new Random()
    emptySlots(rnd.nextInt(emptySlots.length))
  }

  /**
   * Get a textual representation of the board situation
   *
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
