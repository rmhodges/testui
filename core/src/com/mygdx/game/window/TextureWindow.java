package com.mygdx.game.window;

import com.badlogic.gdx.scenes.scene2d.ui.*;

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
        table.add(imageListTable).left().width(200);

        Table imageListContainer = new Table (skin);
        ScrollPane textureScrollPane = new ScrollPane(imageListContainer, skin);

        imageListTable.add(textureScrollPane).height(100);
        imageListContainer.add(new Label("This is a test", skin));
        imageListContainer.add(new Label("aaaaaaaa", skin));
        imageListContainer.add(new Label("bbbbbb", skin));
        imageListContainer.add(new Label("cccccccccc", skin));
        imageListContainer.add(new Label("ddddddddddddd", skin));


        TextButton loadImage = new TextButton("Load Image...",skin);

        table.row();

        table.add(loadImage).right().pad(5);

        mainWindow.pack();
    }


    public Window getMainWindow() {
        return mainWindow;
    }
}
