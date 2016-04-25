package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;

import java.text.NumberFormat;

/**
 * Created by rhodges on 19/04/16.
 */

public class ImageLoader extends ApplicationAdapter {

    Window imageDialog;
    Skin skin;
    Texture texture;
    Label midLabel;
    Label topLabel;
    Label botLabel;
    ShapeRenderer shapeRenderer;
    NumberFormat nf = NumberFormat.getInstance();

    Texture userTexture;
    private SpriteBatch batch;


    private Stage stage;

    public void create () {
        stage = new Stage(new StretchViewport(800, 600));
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        texture= new Texture(Gdx.files.internal("data/badlogicsmall.jpg"));

        userTexture = new Texture(Gdx.files.internal("data/t8890.png"));

        final TextureRegion userImage = new TextureRegion(userTexture);

        final Image sourceImage = new Image(userImage);
        stage.addActor(sourceImage);

        sourceImage.setPosition(100,100);

//        sourceImage.addListener(new DragListener(){
//            @Override
//            public void touchDragged(InputEvent event, float x, float y, int pointer) {
//                super.touchDragged(event, x, y, pointer);
//
//                System.out.println("Position X " + x + ", Position Y " + y);
//
//            }
//
//
//
//            @Override
//            public void dragStop(InputEvent event, float x, float y, int pointer) {
//                super.dragStop(event, x, y, pointer);
////                sourceImage.moveBy(x, y );
////                sourceImage.moveBy(x-(sourceImage.getWidth()/2) , y - sourceImage.getHeight()/2);
//
//            }
//
//            @Override
//            public void dragStart(InputEvent event, float x, float y, int pointer) {
//                super.dragStart(event, x, y, pointer);
//            }
//        });

        batch = new SpriteBatch();
        final DragAndDrop dragAndDrop = new DragAndDrop();

        dragAndDrop.addSource(new DragAndDrop.Source(sourceImage) {

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
//                sourceImage.setBounds(50, 125, sourceImage.getWidth(), sourceImage.getHeight());
//                if(target != null) {
//                    sourceImage.moveBy(x, y);
//                    sourceImage.setPosition(target.getActor().getX(), target.getActor().getY());
//                }
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
//
//        dragAndDrop.addSource(new DragAndDrop.Source(sourceImage) {
//
//            @Override
//            public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
//                DragAndDrop.Payload payload = new Payload();
//
////                payload.setObject(sourceImage);
//                dragAndDrop.setDragActorPosition(-x, -y + sourceImage.getHeight());
//                payload.setDragActor(sourceImage);
//
//                return payload;
//            }
//
//            @Override
//            public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
////                super.dragStop(event, x, y, pointer, payload, target);
//
//                Image i = (Image) payload.getObject();
//
//                sourceImage.setPosition(x,y);
//                stage.addActor(sourceImage);
//            }
//        });

//        dragAndDrop.setDragActorPosition(-(sourceImage.getWidth()/2), sourceImage.getHeight()/2);


        final TextureRegion image = new TextureRegion(texture);

        final Image img = new Image(image);

        final TextureRegion imageFlipped = new TextureRegion(image);
        imageFlipped.flip(true, true);

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(skin.get(Button.ButtonStyle.class));
        style.imageUp = new TextureRegionDrawable(image);
        style.imageDown = new TextureRegionDrawable(imageFlipped);
        ImageButton iconButton = new ImageButton(style);

        img.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println(x + " " + y + " ");

                img.setDrawable(new SpriteDrawable(new Sprite(imageFlipped)));

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                img.setDrawable(new SpriteDrawable(new Sprite(image)));
           }
        });




        Gdx.input.setInputProcessor(stage);



        imageDialog = new Window("Image Dialog", skin );

        Button imgButton = new Button(new Image(image),skin);

        imageDialog.add(imgButton);
//        imageDialog.button("Option 2", 2L);

        imageDialog.add(img);



        midLabel = new Label(stage.getWidth() / 2 + "," + stage.getHeight() / 2, skin);
        midLabel.setColor(Color.GRAY);

        topLabel = new Label(stage.getWidth() / 2 + "," + stage.getHeight(), skin);
        topLabel.setColor(Color.GRAY);

        botLabel = new Label(stage.getWidth() / 2 + "," + 0, skin);
        botLabel.setColor(Color.GRAY);

        imageDialog.add(midLabel);

        stage.addActor(midLabel);
        stage.addActor(topLabel);
        stage.addActor(botLabel);

        stage.addActor(imageDialog);


        midLabel.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
        topLabel.setPosition(stage.getWidth() / 2, stage.getHeight());
        botLabel.setPosition(stage.getWidth() / 2, 0);

        shapeRenderer = new ShapeRenderer();

    }

    public void resize (int width, int height) {
        // See below for what true means.
        stage.getViewport().update(width, height, true);

        midLabel.setText(nf.format(stage.getWidth() / 2) + "," + nf.format(stage.getHeight() / 2));
        midLabel.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);

        topLabel.setText(stage.getWidth() / 2 + "," + stage.getHeight());
        topLabel.setPosition(stage.getWidth() / 2, stage.getHeight() - topLabel.getHeight());

        botLabel.setText(stage.getWidth() / 2 + "," + 0);
        botLabel.setPosition(stage.getWidth() / 2, 0);

    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

//        stage.draw();
//        stage.act(Gdx.graphics.getDeltaTime());



        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.line(stage.getWidth() / 2,0,stage.getWidth() / 2, stage.getHeight(), Color.DARK_GRAY, Color.DARK_GRAY);
        shapeRenderer.end();


        batch.begin();
        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.draw(userTexture, 450, 350);
        batch.end();
    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }

}
