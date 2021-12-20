package ui

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.control.Button
import scalafx.scene.layout.GridPane

object GameView extends GridPane {
  private val buttons = new Array[Button](9)
  for (i <- buttons.indices) {
    buttons(i) = new Button {
      minWidth = 80
      maxWidth = 80
      minHeight = 80
      maxHeight = 80
      text = ""
      onAction = (_: ActionEvent) => {
        println("Button", i, "pressed")
      }
    }
  }
  padding = Insets(10, 10, 10, 10)
  hgap = 10
  vgap = 10

  add(Main.GetBackToMainButton, 0, 0, 3, 1)
  for (i <- buttons.indices) {
    add(buttons(i), i % 3, 1 + i / 3)
  }
}
