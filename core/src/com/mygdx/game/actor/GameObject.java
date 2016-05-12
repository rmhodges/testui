package com.mygdx.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.window.ManipulationWindow;

/**
 * Created by rhodges on 12/05/16.
 */
public class GameObject {

    private final String filename;

    private final Stage stage;

    private final DragAndDrop dragAndDrop = new DragAndDrop();

    private final Skin skin;

    final Image selectedImage;

    public GameObject(final Skin skin, final Stage stage, final String filename) {
        this.skin = skin;
        this.stage = stage;
        this.filename = filename;

        Texture selectedTexture = new Texture(Gdx.files.internal(filename));

        final TextureRegion selectedTextureRegion = new TextureRegion(selectedTexture);

        selectedImage = new Image(selectedTextureRegion);

        selectedImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ManipulationWindow manipulationWindow = new ManipulationWindow(skin, stage, filename);

                stage.addActor(manipulationWindow.getMainWindow());
            }
        });

        dragAndDrop.addSource(new DragAndDrop.Source(selectedImage) {

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                stage.addActor(selectedImage);
            }

            @Override
            public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setDragActor(selectedImage);
                dragAndDrop.setDragActorPosition(-x, -y + selectedImage.getHeight());
                return payload;
            }

        });
    }

    public void addToStage () {
        stage.addActor(selectedImage);

        selectedImage.setPosition(100, 100);
    }

}
