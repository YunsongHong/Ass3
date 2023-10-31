package invaders.important;

import java.net.MalformedURLException;
import java.util.List;
import java.util.ArrayList;

import invaders.entities.EntityViewImpl;
import invaders.entities.SpaceBackground;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import invaders.entities.EntityView;
import invaders.rendering.Renderable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameWindow {
	private final int width;
    private final int height;
	private Scene scene;
    private Pane pane;
    private Label label;
    private Label label1;
    private int value;
    private GameImportant model;
    private List<EntityView> entityViews =  new ArrayList<EntityView>();
    private Renderable background;

    private double xViewportOffset = 0.0;
    private double yViewportOffset = 0.0;
    // private static final double VIEWPORT_MARGIN = 280.0;

	public GameWindow(GameImportant model, int a){
        this.value=a;
        this.model = model;
        //游戏界面大小
		this.width =  model.getGameWidth();
        this.height = model.getGameHeight();

        pane = new Pane();
        label = new Label("得分: 0");
        label.setFont(new Font("System",  20));
        label1 = new Label("");
        VBox vbox = new VBox();
        vbox.setTranslateX(250); // 设置VBox的X坐标为100
        vbox.setTranslateY(200); // 设置VBox的Y坐标为100
        vbox.getChildren().addAll(label1);
        pane.getChildren().add(label);
        pane.getChildren().add(vbox);
        scene = new Scene(pane, width, height);
        //设置背景
        this.background = new SpaceBackground(model, pane);

        KeyboardInputHandler keyboardInputHandler = null;
        try {
            //设置 音效 与监控按键事件
            keyboardInputHandler = new KeyboardInputHandler(this.model);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //调用监控按键的事件
        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

    }

	public void run() {
         Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17), t -> this.draw(label,value,label1)));

         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.play();
    }


    private void draw(Label scoreLabel,int value,Label scoreLabel1){
        model.update(scoreLabel,value,scoreLabel1);

        List<Renderable> renderables = model.getRenderables();
        for (Renderable entity : renderables) {
            boolean notFound = true;
            for (EntityView view : entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }
        }

        for (Renderable entity : renderables){
            if (!entity.isAlive()){
                for (EntityView entityView : entityViews){
                    if (entityView.matchesEntity(entity)){
                        entityView.markForDelete();
                    }
                }
            }
        }

        for (EntityView entityView : entityViews) {
            if (entityView.isMarkedForDelete()) {
//                System.out.println(entityView.getNode());
                pane.getChildren().remove(entityView.getNode());
            }
        }


        model.getGameObjects().removeAll(model.getPendingToRemoveGameObject());
        model.getGameObjects().addAll(model.getPendingToAddGameObject());
        model.getRenderables().removeAll(model.getPendingToRemoveRenderable());
        model.getRenderables().addAll(model.getPendingToAddRenderable());

        model.getPendingToAddGameObject().clear();
        model.getPendingToRemoveGameObject().clear();
        model.getPendingToAddRenderable().clear();
        model.getPendingToRemoveRenderable().clear();

        entityViews.removeIf(EntityView::isMarkedForDelete);

    }

	public Scene getScene() {
        return scene;
    }
}
