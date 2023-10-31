package invaders.create;
import invaders.gameobject.GameObject;
import invaders.physics.Vector2D;

public abstract class Creater {
    public abstract void buildPosition(Vector2D position);
    public abstract void buildLives(int live);
    public abstract GameObject createGameObject();
    public abstract void reset();
}
