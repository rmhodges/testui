package com.mygdx.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhodges on 05/05/16.
 */
public class ImageActor {

    private final String imageFilename;

    private List<ImageSelectionListener> selectionListener = new ArrayList<ImageSelectionListener>();

    private final Texture texture;

    private final Image image;

    public ImageActor(String imageFilename) {
        this.imageFilename = imageFilename;

        texture = new Texture(Gdx.files.internal(imageFilename));

        image = new Image(texture);

        image.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                imageSelectionListener();
            }
        });

    }

    public Image getImage() {
        return image;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void addSelectionListener(ImageSelectionListener imgListener) {
        selectionListener.add(imgListener);
    }

    public void imageSelectionListener() {

//        selectionListener.forEach(item -> item.selected(this));

        for (ImageSelectionListener listener : selectionListener) {
            listener.selected(this);
        }

    }
}
