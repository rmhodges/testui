package com.mygdx.game.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.actor.ImageActor;
import com.mygdx.game.actor.ImageSelectionListener;
import com.mygdx.game.dialog.CommonFileDialog;
import com.mygdx.game.dialog.CommonFileListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhodges on 04/05/16.
 */
public class TextureWindow {

    private List<String> textureFilenames = new ArrayList<String>();

    private String title = "Texture Selection Window";

    private Skin skin;

    private Stage stage;

    private String selectedImageFilename = "data/badlogic.jpg";

    private final Window mainWindow;

    private Table table;

    private List<PasteImageListener> pasteImageListeners = new ArrayList<PasteImageListener>();

    private Image selectedImage;

    {
        textureFilenames.add("images/groundGrass.png");
        textureFilenames.add("images/heli.png");
        textureFilenames.add("images/hill1.png");
        textureFilenames.add("images/barrack.png");
    }

    public void addPasteImageListener(PasteImageListener listener) {
        pasteImageListeners.add(listener);
    }

    public TextureWindow(Skin skin, Stage stage) {

        this.skin = skin;

        this.stage = stage;

        mainWindow = new Window(title, skin);

        table = new Table(skin);

        table.setSize(500, 200);

        mainWindow.add(table);

        Label availableLabel = new Label("Available:", skin);

        table.add(availableLabel).left().pad(5);

        table.row();

        Table imageListTable = new Table(skin);
        table.add(imageListTable).left().width(250);

        Table imageListContainer = new Table(skin);
        ScrollPane textureScrollPane = new ScrollPane(imageListContainer, skin);

        imageListTable.add(textureScrollPane).height(100);


        for (String imageFilename : textureFilenames) {

            ImageActor imgActor = new ImageActor(imageFilename);

            imgActor.addSelectionListener(new ImageSelectionListener() {
                @Override
                public void selected(ImageActor actor) {
                    System.out.println("I have been clicked for image : " + actor.getImageFilename());
                    selectedImageFilename = actor.getImageFilename();
                    displaySelectedImage(selectedImageFilename);
                }
            });

            imageListContainer.add(imgActor.getImage()).height(90).width(190);

        }

        TextButton loadImage = new TextButton("Load Image...", skin);

        loadImage.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadImage();
            }
        });

        table.row();

        table.add(loadImage).pad(10).right();

        table.row();

        Label selectedLabel = new Label("Selected:", skin);

        table.add(selectedLabel).pad(5).left();

        table.row();

        Texture placeholder = new Texture(Gdx.files.internal("data/badlogic.jpg"));

        TextureRegion placeholderRegion = new TextureRegion(placeholder);

        selectedImage = new Image(placeholderRegion);

        table.add(selectedImage).width(200).height(200);

        table.row();

        TextButton pasteImageButton = new TextButton("Paste Image...", skin);

        pasteImageButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                System.out.println("Generate paste image event");

                for (PasteImageListener listener : pasteImageListeners) {
                    listener.pasteImage(selectedImageFilename);
                }
            }
        });

        table.add(pasteImageButton).pad(10).right();

        mainWindow.pack();
    }

    void displaySelectedImage(String filename) {

        Cell<Image> cell = table.getCell(selectedImage);

        Texture placeholder = new Texture(Gdx.files.internal(filename));

        TextureRegion placeholderRegion = new TextureRegion(placeholder);

        selectedImage = new Image(placeholderRegion);

        cell.setActor(selectedImage);
    }

    public void loadImage() {

        System.out.println("Window Load Dialog");

        CommonFileDialog cfd = new CommonFileDialog(skin, new CommonFileListener() {
            @Override
            public void dialogExit() {
                System.out.println("Exit Dialog");
            }

            @Override
            public void loadFile(String file) {

                System.out.println("Adding " + file);
                textureFilenames.add(file);
            }
        });

        cfd.show(stage);

    }

    public Window getMainWindow() {
        return mainWindow;
    }
}
