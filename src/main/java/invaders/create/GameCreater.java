package invaders.create;


import invaders.important.GameImportant;
import invaders.important.GameWindow;
import javafx.stage.Stage;

public class GameCreater {

    private static GameCreater gameCreater;
    public GameCreater(Stage primaryStage,String config) {}

    public static  void gameCreatertart(Stage primaryStage,String config) {

        System.out.println("按钮1被点击了,开始简单模式");
        GameImportant model = new GameImportant(config);
        GameWindow window = new GameWindow(model,1);
        window.run();
        primaryStage.setTitle("飞机大战");
        primaryStage.setScene(window.getScene());
        primaryStage.show();
        window.run();
//        return gameCreater;
    }

}
