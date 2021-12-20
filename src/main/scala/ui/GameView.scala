package ui

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.GridPane
import tictactoe.TicTacToe

object GameView extends GridPane {
  private val buttons = new Array[Button](9)
  for (i <- buttons.indices) {
    buttons(i) = new Button {
      minWidth = 180
      maxWidth = 180
      minHeight = 180
      maxHeight = 180
      text = ""
      style = "-fx-font-size: 50pt"
      onAction = (_: ActionEvent) => {
        if (!game.GameEnded && game.PlaceMarker(i, game.GetCurrentMarker)) {
          text = game.GetMarker(i).toString
          game.SwapTurn()
          turnLabel.text = game.GetCurrentPlayer.name + " turn"
        }
        // Check if the game just ended now
        if (game.GameEnded) {
          turnLabel.text = game.GetWinner match {
            case Some(p) => p.name + " won the game!"
            case None => "Game ended in a tie!"
          }
          // Show "Play again" dialog?
        }
      }
    }
  }
  // Create a label that shows whose turn it is
  private val turnLabel = new Label("Player 1 turn") {
    style = "-fx-font-size: 24pt"
    maxWidth = Double.MaxValue
    alignment = Pos.Center
  }

  private var game: TicTacToe = new TicTacToe()

  padding = Insets(10, 10, 10, 10)
  hgap = 10
  vgap = 10
  alignment = Pos.Center
  add(Main.GetBackToMainButton, 0, 0, 3, 1)
  add(turnLabel, 0, 1, 3, 1)
  for (i <- buttons.indices) {
    add(buttons(i), i % 3, 2 + i / 3)
  }

  /**
   * Starts a new game, either 1 or 2 player game
   *
   * @param ticTacToe instance of the game to be played
   */
  def StartGame(ticTacToe: TicTacToe): Unit = {
    this.game = ticTacToe
    // Also clear all labels
    for (b <- this.buttons)
      b.text = ""
    turnLabel.text = game.GetCurrentPlayer.name + " turn"
  }
}
