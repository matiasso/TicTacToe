package ui

import scalafx.scene.Scene
import scalafx.scene.layout.Pane

import scala.collection.mutable

class SceneController(val scene: Scene) {
  private val map: mutable.HashMap[String, Pane] = new mutable.HashMap[String, Pane]()

  /**
   * Add a scene to the list of scenes
   *
   * @param name of the scene to be added
   * @param pane for the scene to be used
   */
  def addScene(name: String, pane: Pane): Unit = map.put(name, pane)

  /**
   * Remove given scene from all scenes
   *
   * @param name of the scene to be removed
   */
  def removeScene(name: String): Unit = map.remove(name)

  /**
   * Activate given scene
   *
   * @param name of the scene
   */
  def activate(name: String): Unit = {
    if (map.contains(name))
      scene.setRoot(map(name))
  }
}
