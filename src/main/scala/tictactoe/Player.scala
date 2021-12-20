package tictactoe

import tictactoe.Markers.Marker

/**
 * Create a new player object
 *
 * @param name   of the player
 * @param isAI   to differentiate real players from co-op enemies
 * @param marker can be either X or O
 */
class Player(val name: String, val isAI: Boolean, val marker: Marker) {
  private var score = 0

  /**
   * Returns the current players score
   *
   * @return Int indicating the score of the current player
   */
  def GetScore: Int = score

  /**
   * Adds 1 point to this player
   */
  def AddScore(): Unit = score += 1
}
