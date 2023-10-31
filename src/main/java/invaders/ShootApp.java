package invaders;

import invaders.create.GameCreater;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import invaders.important.GameImportant;
import invaders.important.GameWindow;

public class ShootApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button startButton1 = new Button("开始游戏--简单模式");
        Button startButton2 = new Button("开始游戏--一般模式");
        Button startButton3 = new Button("开始游戏--困难模式");
        startButton1.setStyle(
                "-fx-background-color:#00BFFF;"+         //设置背景颜色
                        "-fx-background-radius:20;"+     //设置背景圆角
                        "-fx-text-fill:#FF0000;"+        //设置字体颜色
                        "-fx-border-radius:20;"+         //设置边框圆角
//                        "-fx-border-color:#FFFF00;"+     //设置边框颜色
//                        "-fx-border-style:dashed;"+      //设置边框样式
                        "-fx-border-width:5;"+           //设置边框宽度
                        "-fx-border-insets:-5"           //设置边框插入值
        );
        startButton2.setStyle(
                "-fx-background-color:#00BFFF;"+         //设置背景颜色
                        "-fx-background-radius:20;"+     //设置背景圆角
                        "-fx-text-fill:#FF0000;"+        //设置字体颜色
                        "-fx-border-radius:20;"+         //设置边框圆角
//                        "-fx-border-color:#FFFF00;"+     //设置边框颜色
//                        "-fx-border-style:dashed;"+      //设置边框样式
                        "-fx-border-width:5;"+           //设置边框宽度
                        "-fx-border-insets:-5"           //设置边框插入值
        );
        startButton3.setStyle(
                "-fx-background-color:#00BFFF;"+         //设置背景颜色
                        "-fx-background-radius:20;"+     //设置背景圆角
                        "-fx-text-fill:#FF0000;"+        //设置字体颜色
                        "-fx-border-radius:20;"+         //设置边框圆角
//                        "-fx-border-color:#FFFF00;"+     //设置边框颜色
//                        "-fx-border-style:dashed;"+      //设置边框样式
                        "-fx-border-width:5;"+           //设置边框宽度
                        "-fx-border-insets:-5"           //设置边框插入值
        );
        // 创建一个垂直布局容器
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.getChildren().addAll(startButton1,startButton2,startButton3);

        startButton1.setOnAction(event1 ->{

            GameCreater.gameCreatertart(primaryStage,"src/main/resources/config_easy.json");

        });
        startButton2.setOnAction(event2 ->{

            GameCreater.gameCreatertart(primaryStage,"src/main/resources/config_hard.json");

        });
        startButton3.setOnAction(even3 ->{
            GameCreater.gameCreatertart(primaryStage,"src/main/resources/config_medium.json");

        });

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setTitle("飞机大战");
        primaryStage.setScene(scene);
        primaryStage.show();



    }
}
