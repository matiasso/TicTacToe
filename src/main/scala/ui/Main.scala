package ui

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.paint.Color

object Main extends JFXApp3 {

  var sceneController: Option[SceneController] = None // Initialize null controller but define it at start()
  val mainViewStr: String = "main-menu"
  val gameViewStr: String = "game-view"
  val difficultyViewStr: String = "difficulty-selector"

  /**
   * Overrides the default start() method of JFXApp3 to create PrimaryStage
   */
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      width = 800
      height = 800
      resizable = false
      title = "TicTacToe made by Matias"
      scene = new Scene {
        fill = Color.rgb(38, 38, 38)
      }
    }
    // Generate a sceneController with different scenes
    sceneController = Some(new SceneController(stage.getScene))
    sceneController match {
      case None => throw new Exception("SceneController was empty even though it was just defined")
      case Some(sc) =>
        sc.addScene(mainViewStr, MenuView)
        sc.addScene(gameViewStr, GameView)
        sc.addScene(difficultyViewStr, DifficultySelectorView)
        sc.activate("main-menu")
    }
  }

  /**
   * Activates given scene
   *
   * @param name of the scene that will be activated
   */
  def ChangeScene(name: String): Unit = {
    sceneController match {
      case None => throw new Exception("SceneController was empty for some reason")
      case Some(sc) => sc.activate(name)
    }
  }

  /**
   * Returns a new button that will return to the main menu
   *
   * @return new Button
   */
  def GetBackToMainButton: Button = {
    new Button("Back to main menu") {
      maxHeight = 35
      minHeight = 35
      onAction = (_: ActionEvent) => {
        ChangeScene(mainViewStr)
      }
    }
  }
}
