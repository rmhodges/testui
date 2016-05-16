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

        // Details
        Label filenameLabel = new Label("filename", skin);
        Label filenameField = new Label(selectedImageFilename, skin);

        filenameLabel.setFontScale(0.8f);
        filenameField.setFontScale(0.8f);

        table.add(filenameLabel);
        table.add(filenameField).colspan(3);

        table.row().padBottom(10);

        Label nameLabel = new Label("name", skin);
        TextField nameField = new TextField("TODO", skin);

        table.add(nameLabel);
        table.add(nameField).colspan(3);

        table.row();



        // Image
        Texture placeholder = new Texture(Gdx.files.internal(selectedImageFilename));
        TextureRegion placeholderRegion = new TextureRegion(placeholder);
        Image selectedImage = new Image(placeholderRegion);
        table.add(selectedImage).width(200).height(200).colspan(4);


        // Rotation and Scaling
        table.row();

        Label scaleLabel = new Label("scale", skin);
        TextField scaleField = new TextField("0", skin);
        TextButton scaleSmallButton = new TextButton("-", skin);
        TextButton scaleLargeButton = new TextButton("+", skin);

        table.add(scaleLabel).padTop(20);
        table.add(scaleField).width(50).padTop(20);
        table.add(scaleSmallButton).width(15).right().padTop(20);
        table.add(scaleLargeButton).width(15).left().padTop(20);
        table.row();

        Label rotateLabel = new Label("rotate", skin);
        TextField rotateField = new TextField("0", skin);
        TextButton rotateSmallButton = new TextButton("-", skin);
        TextButton rotateLargeButton = new TextButton("+", skin);

        table.add(rotateLabel);
        table.add(rotateField).width(50);
        table.add(rotateSmallButton).width(15).right();
        table.add(rotateLargeButton).width(15).left();

        // Physics
        table.row();

        CheckBox enablePhysicsCheckBox = new CheckBox("enable physics", skin);
        table.add(enablePhysicsCheckBox).colspan(4).left().padTop(20);
        table.row();

        Label fixtureLabel = new Label("fixture", skin);
        TextField fixtureField = new TextField("1", skin);

        table.add(fixtureLabel).colspan(3);
        table.add(fixtureField);


        // Close Window

        table.row ();

        TextButton doneButton = new TextButton("done", skin);

        doneButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                System.out.println("Close Windows Event");

            }
        });

        table.add(doneButton).pad(10).right().colspan(4);

        mainWindow.pack();
    }

    public Window getMainWindow() {
        return mainWindow;
    }
}
