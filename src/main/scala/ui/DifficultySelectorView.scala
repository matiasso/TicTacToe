package ui

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox

object DifficultySelectorView extends VBox {
  children = Seq(
    // Make this "Back to main button" somewhere
    new Button("Back to main") {
      maxWidth = 80
      minWidth = 80
      maxHeight = 35
      minHeight = 35
      onAction = (_: ActionEvent) => {
        Main.ChangeScene(Main.mainViewStr)
      }
    }
    // Add 3 different difficulties here
  )
}
