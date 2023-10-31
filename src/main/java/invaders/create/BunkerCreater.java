package invaders.create;

import invaders.gameobject.Bunker;
import invaders.gameobject.GameObject;
import invaders.physics.Vector2D;
import javafx.scene.image.Image;

public class BunkerCreater extends Creater {
    private Bunker bunker;

    @Override
    public void buildPosition(Vector2D position) {
        bunker.setPosition(position);
    }

    @Override
    public void buildLives(int lives) {
        bunker.setLives(lives);
    }

    @Override
    public GameObject createGameObject() {
        return (GameObject) this.bunker;
    }

    @Override
    public void reset() {
        bunker = new Bunker();
    }

    public void buildImage(Image image) {
        bunker.setImage(image);
        bunker.setWidth((int) image.getWidth());
        bunker.setHeight((int) image.getHeight());
    }
}
