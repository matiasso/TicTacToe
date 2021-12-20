package ui

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{Pane, VBox}
import tictactoe.Difficulties._
import tictactoe.{Difficulties, TicTacToe}

object DifficultySelectorView extends VBox {
  val difficultyButtons: Array[Button] = new Array[Button](Difficulties.values.size)
  for (i <- Difficulties.values) {
    // The IDs go from 0 to 2
    difficultyButtons(i.id) = new Button {
      // Rewrite difficulties with enum or something
      text = Difficulties(i.id).toString
      alignment = Pos.Center
      minWidth = 300
      maxWidth = Double.MaxValue
      minHeight = 50
      maxHeight = 50
      onAction = (_: ActionEvent) => {
        i match {
          case Easy => GameView.StartGame(new TicTacToe(true, Easy))
          case Normal => GameView.StartGame(new TicTacToe(true, Normal))
          case Impossible => GameView.StartGame(new TicTacToe(true, Impossible))
          case _ => throw new Exception("Unknown difficulty selected!")
        }
        Main.ChangeScene(Main.gameViewStr)
      }
    }
  }
  padding = Insets(10, 10, 10, 10)
  spacing = 20
  alignment = Pos.Center
  children = Seq(
    new Pane {
      alignment = Pos.TopLeft
      children = Main.GetBackToMainButton
    },
    new Label("Choose difficulty") {
      maxWidth = Double.MaxValue
      alignment = Pos.Center
      style = "-fx-font-size: 30pt"
    }
  ) ++ difficultyButtons
}
