package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.actor.GameObject;
import com.mygdx.game.dialog.CommonFileDialog;
import com.mygdx.game.dialog.CommonFileListener;
import com.mygdx.game.stage.LayoutGridStage;
import com.mygdx.game.window.ManipulationWindow;
import com.mygdx.game.window.PasteImageListener;
import com.mygdx.game.window.TextureWindow;

import java.text.NumberFormat;
import java.util.ArrayList;


/**
 * Created by rhodges on 19/04/16.
 */

public class ImageLayoutTool extends ApplicationAdapter {

    Skin skin;

    private LayoutGridStage gridStage;

    Texture userTexture;

    private SpriteBatch batch;

    private java.util.List<String> selectedFiles = new ArrayList<String>();

    private Stage stage;

    final DragAndDrop dragAndDrop = new DragAndDrop();


    public void create () {
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//        skin = new Skin(Gdx.files.internal("commodore/commodore64ui/uiskin.json"));

        gridStage = new LayoutGridStage(skin);

        stage = gridStage.getStage();

        userTexture = new Texture(Gdx.files.internal("data/t8890.png"));

        final TextureRegion userImage = new TextureRegion(userTexture);

        final Image sourceImage = new Image(userImage);
        stage.addActor(sourceImage);

        sourceImage.setPosition(100,100);

        batch = new SpriteBatch();

        dragAndDrop.addSource(new DragAndDrop.Source(sourceImage) {

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
                stage.addActor(sourceImage);
            }

            @Override
            public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setDragActor(sourceImage);
                dragAndDrop.setDragActorPosition(-x, -y + sourceImage.getHeight());
                return payload;
            }

        });



        Gdx.input.setInputProcessor(stage);


        final TextureWindow textureWindow = new TextureWindow(skin, stage);

        textureWindow.addPasteImageListener(new PasteImageListener() {
            @Override
            public void pasteImage(final String filename) {

                GameObject gameObject = new GameObject(skin, stage, filename);

                gameObject.addToStage();

            }
        });

        Window mainWindow = textureWindow.getMainWindow();

        stage.addActor(mainWindow);


    }


    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }



    @Override
    public void render()
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gridStage.render();


    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }

}
