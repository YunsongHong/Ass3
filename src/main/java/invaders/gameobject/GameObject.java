package invaders.gameobject;

import invaders.important.GameImportant;

// contains basic methods that all GameObjects must implement
public interface GameObject {

    public void start();
    public void update(GameImportant model);

}
