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
import com.mygdx.game.dialog.CommonFileDialog;
import com.mygdx.game.dialog.CommonFileListener;
import com.mygdx.game.stage.LayoutGridStage;
import com.mygdx.game.window.PasteImageListener;
import com.mygdx.game.window.TextureWindow;

import java.text.NumberFormat;
import java.util.ArrayList;


/**
 * Created by rhodges on 19/04/16.
 */

public class ImageLayoutTool extends ApplicationAdapter {

//    Window imageDialog;


    Skin skin;
    Texture texture;
//    Label midLabel;
//    Label topLabel;
//    Label botLabel;

    private LayoutGridStage gridStage;

    ShapeRenderer shapeRenderer;

    Texture userTexture;

    private SpriteBatch batch;

    private java.util.List<String> selectedFiles = new ArrayList<String>();

    private Stage stage;

    public void create () {
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//        skin = new Skin(Gdx.files.internal("commodore/commodore64ui/uiskin.json"));

        gridStage = new LayoutGridStage(skin);

        stage = gridStage.getStage();

//        texture= new Texture(Gdx.files.internal("data/badlogicsmall.jpg"));

        userTexture = new Texture(Gdx.files.internal("data/t8890.png"));

        final TextureRegion userImage = new TextureRegion(userTexture);

        final Image sourceImage = new Image(userImage);
        stage.addActor(sourceImage);

        sourceImage.setPosition(100,100);

        batch = new SpriteBatch();
        final DragAndDrop dragAndDrop = new DragAndDrop();

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

//        final TextureRegion image = new TextureRegion(texture);

//        final Image img = new Image(image);


//        Target target = new Target(img) {
//
//            @Override
//            public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
//                System.out.println("Drag");
//                return false;
//            }
//
//            @Override
//            public void drop(Source source, Payload payload, float x, float y, int pointer) {
//
//                System.out.println("Drop");
//            }
//        };

//        final TextureRegion imageFlipped = new TextureRegion(image);
//        imageFlipped.flip(true, true);

//        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(skin.get(Button.ButtonStyle.class));
//        style.imageUp = new TextureRegionDrawable(image);
//        style.imageDown = new TextureRegionDrawable(imageFlipped);

//        ImageButton iconButton = new ImageButton(style);

//        img.addListener(new ClickListener(){
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                System.out.println(x + " " + y + " ");
//
//                img.setDrawable(new SpriteDrawable(new Sprite(imageFlipped)));
//
//                return true;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                img.setDrawable(new SpriteDrawable(new Sprite(image)));
//           }
//        });




        Gdx.input.setInputProcessor(stage);



//        imageDialog = new Window("Image Dialog", skin );

//        Button imgButton = new Button(new Image(image),skin);
//
//        imageDialog.add(imgButton);
//
//        imageDialog.add(img);


//        midLabel = new Label(stage.getWidth() / 2 + "," + stage.getHeight() / 2, skin);
//        midLabel.setColor(Color.GRAY);
//
//        topLabel = new Label(stage.getWidth() / 2 + "," + stage.getHeight(), skin);
//        topLabel.setColor(Color.GRAY);
//
//        botLabel = new Label(stage.getWidth() / 2 + "," + 0, skin);
//        botLabel.setColor(Color.GRAY);



//        stage.addActor(midLabel);
//        stage.addActor(topLabel);
//        stage.addActor(botLabel);

//        stage.addActor(imageDialog);


//        midLabel.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
//        topLabel.setPosition(stage.getWidth() / 2, stage.getHeight());
//        botLabel.setPosition(stage.getWidth() / 2, 0);

//        shapeRenderer = new ShapeRenderer();


//        CommonFileDialog cfd = new CommonFileDialog(skin, new CommonFileListener() {
//            @Override
//            public void dialogExit() {
//                System.out.println("Exit Dialog");
//            }
//
//            @Override
//            public void loadFile(String file) {
//
//                System.out.println("Adding " + file);
//                selectedFiles.add(file);
//            }
//        });


//        cfd.show(stage);


        TextureWindow textureWindow = new TextureWindow(skin, stage);

        textureWindow.addPasteImageListener(new PasteImageListener() {
            @Override
            public void pasteImage(String filename) {

                Texture selectedTexture = new Texture(Gdx.files.internal(filename));

                final TextureRegion selectedTextureRegion = new TextureRegion(selectedTexture);

                final Image selectedImage = new Image(selectedTextureRegion);

                stage.addActor(selectedImage);

                selectedImage.setPosition(100,100);


            }
        });

        stage.addActor(textureWindow.getMainWindow());

    }


    public void resize (int width, int height) {
        // See below for what true means.
//        stage.getViewport().update(width, height, true);



//        midLabel.setText(nf.format(stage.getWidth() / 2) + "," + nf.format(stage.getHeight() / 2));
//        midLabel.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
//
//        topLabel.setText(stage.getWidth() / 2 + "," + stage.getHeight());
//        topLabel.setPosition(stage.getWidth() / 2, stage.getHeight() - topLabel.getHeight());
//
//        botLabel.setText(stage.getWidth() / 2 + "," + 0);
//        botLabel.setPosition(stage.getWidth() / 2, 0);

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
