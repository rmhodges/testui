package com.mygdx.game.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.actor.ImageActor;
import com.mygdx.game.actor.ImageSelectionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhodges on 04/05/16.
 */
public class TextureWindow {

    private List<String> textureFilenames = new ArrayList<String>();

    private String title = "Texture Selection Window";

    private Skin skin;

    private final Window mainWindow;


    {
        textureFilenames.add("data/animation.png");
        textureFilenames.add("data/debug.png");
        textureFilenames.add("data/default.png");
        textureFilenames.add("data/animation.png");
    }

    public TextureWindow(Skin skin) {

        this.skin = skin;

        mainWindow = new Window(title, skin);

        Table table = new Table(skin);

        table.setSize(500,200);

        mainWindow.add(table);

        Label availableLabel = new Label("Available:", skin);

        table.add(availableLabel).left().pad(5);

        table.row();

        Table imageListTable = new Table (skin);
        table.add(imageListTable).left().width(400);

        Table imageListContainer = new Table (skin);
        ScrollPane textureScrollPane = new ScrollPane(imageListContainer, skin);

        imageListTable.add(textureScrollPane).height(100);


        for (String imageFilename : textureFilenames){

            ImageActor imgActor = new ImageActor(imageFilename);

            imgActor.addSelectionListener(new ImageSelectionListener() {
                @Override
                public void selected(ImageActor actor) {
                    System.out.println("I have been clicked for image : " + actor.getImageFilename());
                }
            });

            imageListContainer.add(imgActor.getImage()).height(99);

        }

        TextButton loadImage = new TextButton("Load Image...",skin);

        table.row();

        table.add(loadImage).right().pad(5);

        mainWindow.pack();
    }


    public Window getMainWindow() {
        return mainWindow;
    }
}
