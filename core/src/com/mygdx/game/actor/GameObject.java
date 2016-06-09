package com.mygdx.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.stage.VirtualStage;
import com.mygdx.game.window.ManipulationWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhodges on 12/05/16.
 */
public class GameObject {

    private boolean selected = false;

    private List<Image> boundaryHandles = new ArrayList<Image>(4);

    private final String filename;

    private final VirtualStage stage;

    private final DragAndDrop dragAndDrop = new DragAndDrop();

    private final Skin skin;

    final Image selectedImage;

    private List<Vector2> boundary = new ArrayList<Vector2>();

    public GameObject(final Skin skin, final VirtualStage stage, final String filename) {
        this.skin = skin;
        this.stage = stage;
        this.filename = filename;

        final Texture selectedTexture = new Texture(Gdx.files.internal(filename));

        final TextureRegion selectedTextureRegion = new TextureRegion(selectedTexture);

        selectedImage = new Image(selectedTextureRegion);

        selectedImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ManipulationWindow manipulationWindow = new ManipulationWindow(skin, stage.getStage(), filename);

                stage.getStage().addActor(manipulationWindow.getMainWindow());
            }
        });

        dragAndDrop.addSource(new DragAndDrop.Source(selectedImage) {

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {

                stage.getStage().addActor(selectedImage);

            }

            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setDragActor(selectedImage);

                System.out.println("X: " + x + "  Y: " + y);

                dragAndDrop.setDragActorPosition(-x, -y + selectedImage.getHeight());
                return payload;
            }

        });

    }

    public Actor getActor() {
        return selectedImage;
    }

    public void actorAddedToStage (){
        initialiseHandles ();
        addHandlesToStage();
    }



    public List<Vector2> collisionBoundaries() {

        boundary.clear ();

        boundary.add(new Vector2(selectedImage.getX(), selectedImage.getY()));
        boundary.add(new Vector2(selectedImage.getX() + selectedImage.getImageWidth(), selectedImage.getY()));
        boundary.add(new Vector2(selectedImage.getX() + selectedImage.getImageWidth(), selectedImage.getY() + selectedImage.getImageHeight()));
        boundary.add(new Vector2(selectedImage.getX(), selectedImage.getY() + selectedImage.getImageHeight()));

        if (boundaryHandles.size() == 4){
            boundaryHandles.get(0).setPosition(selectedImage.getX() - 10, selectedImage.getY() - 10);
            boundaryHandles.get(1).setPosition(selectedImage.getX()  + selectedImage.getImageWidth(), selectedImage.getY() - 10);
            boundaryHandles.get(2).setPosition(selectedImage.getX() + selectedImage.getImageWidth(), selectedImage.getY() + selectedImage.getImageHeight());
            boundaryHandles.get(3).setPosition(selectedImage.getX() - 10, selectedImage.getY() + selectedImage.getImageHeight());

        }

        return boundary;
    }

    private void addHandlesToStage() {

        for (Image boundaryHandle : boundaryHandles) {

            stage.getStage().addActor(boundaryHandle);

        }
    }



    private void initialiseHandles() {

        boundaryHandles.clear();

        List<Vector2> collisionBoundaries = collisionBoundaries();

        for (Vector2 point : collisionBoundaries) {

            Pixmap pm = new Pixmap(10, 10, Pixmap.Format.RGBA8888);

            pm.setColor(Color.GOLD);

            pm.fill();

            final Image im = new Image(new Texture(pm));

            im.setPosition(point.x, point.y);

            dragAndDrop.addSource(new DragAndDrop.Source(im){

                @Override
                public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {

                    stage.getStage().addActor(im);

                }

                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {

                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setDragActor(im);

                    System.out.println("X: " + x + "  Y: " + y);

                    dragAndDrop.setDragActorPosition(-x, -y + im.getHeight());
                    return payload;

                }
            });

            boundaryHandles.add(im);
        }


    }

}


