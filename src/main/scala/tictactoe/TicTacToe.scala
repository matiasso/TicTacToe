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
      case Normal => GetBestMove(1)
      case Impossible => GetBestMove(9)
      case _ => throw new Exception("Unknown difficulty! Could not execute AI move")
    }
    PlaceMarker(index, player2.marker)
    index
  }

  /**
   * A method that does the actual recursive minimax algorithm
   *
   * @param depth      how deep inside the recursive calls are we? (Pass 0 in at first)
   * @param board      needs to be a clone of current grid
   * @param maximizing boolean whether we start maximizing or minimizing the value
   * @param maxDepth   how deep into the recursive calls can we go at most
   * @return
   */
  def minimax(depth: Int, board: Array[Array[Marker]], maximizing: Boolean, maxDepth: Int = 9): Int = {
    // Define inner evaluation method to "rank" all situations
    def evaluate(board: Array[Array[Marker]]): Int = {
      GetWinner(board) match {
        case Some(p) => if (p.isAI) 10 else -10
        case None => 0
      }
    }

    // Check that we haven't exceeded maximum depth
    if (depth > maxDepth) {
      return 0
    }
    // check the current state of the board
    val score = evaluate(board)
    if (score == 10) {
      return 10 - depth
    } else if (score == -10) {
      return depth - 10
    }
    if (BoardFull(board)) {
      return 0
    }
    var best = if (maximizing) -1000 else 1000
    // Get all the moves
    for (i <- board.indices) {
      for (j <- board(0).indices) {
        if (board(i)(j) == Empty) {
          // Mark the location
          board(i)(j) = if (maximizing) O else X
          val minMaxResult = minimax(depth + 1, board, !maximizing, maxDepth)
          best = if (maximizing) math.max(minMaxResult, best) else math.min(minMaxResult, best)
          // Undo the move
          board(i)(j) = Empty
        }
      }
    }
    best
  }

  /**
   * A method that tries all possible moves and returns the best one according to minimax algorithm
   *
   * @param maxDepth how deep we want to go into recursive calls (how many turns ahead)
   * @return Int index where the computer should place its marker
   */
  def GetBestMove(maxDepth: Int): Int = {
    var bestMove = -1
    var bestVal = -100
    // Copy current grid
    val boardCopy = Array.fill[Array[Marker]](3)(Array.fill[Marker](3)(Markers.Empty))
    for (i <- grid.indices) {
      for (j <- grid(0).indices) {
        boardCopy(i)(j) = grid(i)(j)
      }
    }
    // Try all moves for AI
    for (i <- boardCopy.indices) {
      for (j <- boardCopy(0).indices) {
        if (boardCopy(i)(j) == Empty) {
          // Mark as computer's marker
          boardCopy(i)(j) = O
          val moveVal = minimax(0, boardCopy, maximizing = false, maxDepth)
          if (moveVal > bestVal) {
            bestVal = moveVal
            bestMove = i * 3 + j
          }
          // Undo the move
          boardCopy(i)(j) = Empty
        }
      }
    }
    bestMove
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
  def GameEnded(board: Array[Array[Marker]] = grid): Boolean = BoardFull() || GetWinner().isDefined

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
