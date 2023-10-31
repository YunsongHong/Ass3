package invaders.factory;

import invaders.important.GameImportant;
import invaders.physics.Vector2D;
import invaders.strategy.ProjectileStrategy;
import javafx.scene.image.Image;

import java.io.File;

public class PlayerProjectile extends Projectile {
    private ProjectileStrategy strategy;

    public PlayerProjectile(Vector2D position, ProjectileStrategy strategy) {
        super(position, new Image(new File("src/main/resources/player_shot.png").toURI().toString(), 10, 10, true, true));
        this.strategy = strategy;
    }
    @Override
    public void update(GameImportant model) {
        strategy.update(this);
//        System.out.println(this.getPosition().getY());
//        System.out.println(this.getImage().getHeight());
        if(this.getPosition().getY() <= this.getImage().getHeight()){
//            System.out.println("是这个？？");
            this.takeDamage(1);
        }
    }
    @Override
    public String getRenderableObjectName() {
        return "PlayerProjectile";
    }
}
