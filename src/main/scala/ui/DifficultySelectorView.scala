package ui

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{Pane, VBox}

object DifficultySelectorView extends VBox {
  val difficultyButtons: Array[Button] = new Array[Button](3)
  for (i <- difficultyButtons.indices) {
    difficultyButtons(i) = new Button {
      // Rewrite difficulties with enum or something
      text = if (i == 0) "Easy" else if (i == 1) "Normal" else "Impossible"
      alignment = Pos.Center
      minWidth = 300
      maxWidth = Double.MaxValue
      minHeight = 50
      maxHeight = 50
      onAction = (_: ActionEvent) => {
        i match {
          case 0 => println("Easy selected!")
          case 1 => println("Normal selected!")
          case _ => println("Impossible selected!")
        }
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
