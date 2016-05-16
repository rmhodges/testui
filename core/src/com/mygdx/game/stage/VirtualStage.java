package com.mygdx.game.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actor.GameObject;

/**
 * Created by rhodges on 16/05/16.
 */
public interface VirtualStage {

    void addVirtualSprite (GameObject gameObject);

    Stage getStage ();

    void renderStage ();
}
