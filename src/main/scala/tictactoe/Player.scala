package tictactoe

import tictactoe.Markers.Marker

/**
 * Create a new player object
 * @param name of the player
 * @param isAI to differentiate real players from co-op enemies
 * @param marker can be either X or O
 * @param difficulty difficulty of the co-op player
 */
class Player(val name: String, val isAI: Boolean, val marker: Marker, var difficulty: Short = 1) {
  var score = 0
  // Make enum for difficulty?
  if (difficulty < 0)
    difficulty = 0
  else if (difficulty > 3)
    difficulty = 3
}
