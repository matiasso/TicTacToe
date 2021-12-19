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

object MenuView extends VBox {
  alignment = Pos.Center
  padding = Insets(10, 10, 10, 10)
  children = Seq(
    new HBox {
      padding = Insets(10, 10, 10, 10)
      children = Seq(
        new Text {
          text = "TicTacToe"
          style = "-fx-font: normal bold 50pt sans-serif"
          fill = new LinearGradient(
            endX = 0,
            stops = Stops(Green, DarkRed))
        },
        new Text {
          text = "made by Matias"
          style = "-fx-font: italic bold 36pt sans-serif"
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
        println("Button 1p clicked!")
        // Start 2 player game
        Main.ChangeScene(Main.difficultyViewStr)
      }
    },
    new Button("Start 2 player game") {
      maxWidth = 150
      minWidth = 150
      maxHeight = 50
      minHeight = 50
      onAction = (_: ActionEvent) => {
        println("Button 2p clicked!")
        // Start 2 player game
        Main.ChangeScene(Main.gameViewStr)
      }
    },
  )
}
