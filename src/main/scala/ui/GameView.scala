package ui

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.GridPane

object GameView extends GridPane {
  private val buttons = new Array[Button](9)
  for (i <- buttons.indices) {
    buttons(i) = new Button {
      minWidth = 180
      maxWidth = 180
      minHeight = 180
      maxHeight = 180
      text = ""
      onAction = (_: ActionEvent) => {
        println("Button", i, "pressed")
      }
    }
  }
  // Create a label that shows whose turn it is
  private val turnLabel = new Label("Player 1 turn") {
    maxWidth = Double.MaxValue
    alignment = Pos.Center
  }
  padding = Insets(10, 10, 10, 10)
  hgap = 10
  vgap = 10
  alignment = Pos.Center
  add(Main.GetBackToMainButton, 0, 0, 3, 1)
  add(turnLabel, 0, 1, 3, 1)
  for (i <- buttons.indices) {
    add(buttons(i), i % 3, 2 + i / 3)
  }
}
