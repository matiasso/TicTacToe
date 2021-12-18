import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.paint._
import scalafx.scene.text.Text
import tictactoe.{Markers, Player, TicTacToe}

object UI extends JFXApp3 {
  override def start(): Unit = {
    val player1 = new Player("Matti", false, Markers.X)
    val player2 = new Player("Pekka", false, Markers.O)
    val tictactoe = new TicTacToe(player1, player2)
    while (!tictactoe.BoardFull && tictactoe.GetWinner.isEmpty) {
      val rndFreeSpot = tictactoe.RandomFreeSpot
      tictactoe.PlaceMarker(rndFreeSpot._1, rndFreeSpot._2, tictactoe.GetCurrentMarker)
      tictactoe.SwapTurn()
      println(tictactoe.StringRepresentation)
    }
    println(tictactoe.StringRepresentation)
    tictactoe.GetWinner match {
      case Some(winner) => println("Winner:", winner.name)
      case None => println("Tied")
    }
    val buttons = new Array[Button](9)
    for (i <- buttons.indices){
      buttons(i) = new Button {
        minWidth = 80
        maxWidth = 80
        minHeight = 80
        maxHeight = 80
        text = i.toString
      }
    }
    val HBoxes = new Array[HBox](3)
    for (i <- HBoxes.indices){
      HBoxes(i) = new HBox {
        alignment = Pos.Center
        padding = Insets(10, 10, 10, 10)
        spacing = 10
        children = buttons.grouped(3).toSeq(i)
      }
    }
    stage = new JFXApp3.PrimaryStage {
      //    initStyle(StageStyle.Unified)
      title = "ScalaFX Hello World"
      scene = new Scene {
        fill = Color.rgb(38, 38, 38)
        content = new VBox {
          alignment = Pos.Center
          padding = Insets(50, 80, 50, 80)
          children = Seq(
            new HBox {
              padding = Insets(10, 10, 10, 10)
              children = Seq(
                new Text {
                  text = "TicTacToe"
                  style = "-fx-font: normal bold 70pt sans-serif"
                  fill = new LinearGradient(
                    endX = 0,
                    stops = Stops(Green, DarkRed))
                },
                new Text {
                  text = "made by Matias"
                  style = "-fx-font: italic bold 40pt sans-serif"
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
            HBoxes(0), HBoxes(1), HBoxes(2)
          )
        }
      }
    }
  }
}