package com.mygdx.game.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by rhodges on 12/05/16.
 */
public class ManipulationWindow {

    private String title = "Image Manipulation Window";

    private Skin skin;

    private Stage stage;

    private final Window mainWindow;

    private Table table;

    private String selectedImageFilename;

    public ManipulationWindow(Skin skin, Stage stage, String selectedImageFilename) {
        this.skin = skin;
        this.stage = stage;
        this.selectedImageFilename = selectedImageFilename;

        mainWindow = new Window(title, skin);

        table = new Table(skin);

        table.setSize(300, 300);

        mainWindow.add(table);

        Texture placeholder = new Texture(Gdx.files.internal(selectedImageFilename));

        TextureRegion placeholderRegion = new TextureRegion(placeholder);

        Image selectedImage = new Image(placeholderRegion);

        table.add(selectedImage).width(200).height(200);


        table.row ();

        TextButton doneButton = new TextButton("done", skin);

        doneButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                System.out.println("Close Windows Event");

            }
        });

        table.add(doneButton).pad(10).right();

        mainWindow.pack();
    }

    public Window getMainWindow() {
        return mainWindow;
    }
}
