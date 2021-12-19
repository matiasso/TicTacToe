package ui

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Button
import scalafx.scene.layout.{HBox, VBox}

object GameView extends VBox {
  private val buttons = new Array[Button](9)
  for (i <- buttons.indices) {
    buttons(i) = new Button {
      minWidth = 80
      maxWidth = 80
      minHeight = 80
      maxHeight = 80
      text = i.toString
    }
  }
  private val HBoxes = new Array[HBox](3)
  for (i <- HBoxes.indices) {
    HBoxes(i) = new HBox {
      alignment = Pos.Center
      padding = Insets(10, 10, 10, 10)
      spacing = 10
      children = buttons.grouped(3).toSeq(i)
    }
  }
  children = Seq(
    new Button("Back to main") {
      maxWidth = 80
      minWidth = 80
      maxHeight = 35
      minHeight = 35
      onAction = (_: ActionEvent) => {
        Main.ChangeScene(Main.mainViewStr)
      }
    }, HBoxes(0), HBoxes(1), HBoxes(2))
}
