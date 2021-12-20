package ui

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Button
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text
import tictactoe.TicTacToe

object MenuView extends VBox {
  alignment = Pos.Center
  spacing = 10
  padding = Insets(10, 10, 10, 10)
  children = Seq(
    new HBox {
      padding = Insets(10, 10, 10, 10)
      children = Seq(
        new Text {
          text = "TicTacToe"
          style = "-fx-font: normal bold 48pt sans-serif"
          fill = new LinearGradient(
            endX = 0,
            stops = Stops(Green, LightBlue))
        },
        new Text {
          text = "made by Matias"
          style = "-fx-font: italic bold 34pt sans-serif"
          fill = new LinearGradient(
            endX = 0,
            stops = Stops(Blue, DarkGray)
          )
          effect = new DropShadow {
            color = DarkGray
            radius = 20
            spread = 0.30
          }
        }
      )
    },
    new Button("Start 1 player game") {
      maxWidth = 150
      minWidth = 150
      maxHeight = 50
      minHeight = 50
      onAction = (_: ActionEvent) => {
        Main.ChangeScene(Main.difficultyViewStr)
      }
    },
    new Button("Start 2 player game") {
      maxWidth = 150
      minWidth = 150
      maxHeight = 50
      minHeight = 50
      onAction = (_: ActionEvent) => {
        GameView.StartGame(new TicTacToe())
        Main.ChangeScene(Main.gameViewStr)
      }
    },
  )
}
