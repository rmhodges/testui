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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
    private List<Vector2> userOffset = new ArrayList<Vector2>(4);
    private List<Vector2> imageOffset = new ArrayList<Vector2>(4);


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

        selectedImage.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                System.out.println("CHANGED!!!");
            }
        });


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


    private void calculateImageBoundaryHandles(){

        boolean initialisePosition = true;

        for (Vector2 offsetVector : imageOffset){
            if (offsetVector.x != 0 || offsetVector.y != 0){
                initialisePosition = false;
            }
        }

        boolean imagePositionChange = true;

        if (initialisePosition){

            imageOffset.clear();

            imageOffset.add(new Vector2(selectedImage.getX(), selectedImage.getY()));
            imageOffset.add(new Vector2(selectedImage.getX()  + selectedImage.getImageWidth(), selectedImage.getY()));
            imageOffset.add(new Vector2(selectedImage.getX() + selectedImage.getImageWidth(), selectedImage.getY() + selectedImage.getImageHeight()));
            imageOffset.add(new Vector2(selectedImage.getX(), selectedImage.getY() + selectedImage.getImageHeight()));

        } else {
            imagePositionChange = hasImagePositionChanged ();
        }

        if ( imagePositionChange ){
            if (boundaryHandles.size() == 4){
                boundaryHandles.get(0).setPosition(imageOffset.get(0).x , imageOffset.get(0).y);
                boundaryHandles.get(1).setPosition(imageOffset.get(1).x , imageOffset.get(1).y);
                boundaryHandles.get(2).setPosition(imageOffset.get(2).x , imageOffset.get(2).y);
                boundaryHandles.get(3).setPosition(imageOffset.get(3).x , imageOffset.get(3).y);

                imageOffset.clear();

                imageOffset.add(new Vector2(selectedImage.getX(), selectedImage.getY()));
                imageOffset.add(new Vector2(selectedImage.getX()  + selectedImage.getImageWidth(), selectedImage.getY()));
                imageOffset.add(new Vector2(selectedImage.getX() + selectedImage.getImageWidth(), selectedImage.getY() + selectedImage.getImageHeight()));
                imageOffset.add(new Vector2(selectedImage.getX(), selectedImage.getY() + selectedImage.getImageHeight()));

            }

        }


    }

    private boolean hasImagePositionChanged (){

        if (imageOffset.get(0).x == selectedImage.getX() && imageOffset.get(0).y == selectedImage.getY() )
        {
            if (imageOffset.get(1).x == selectedImage.getX() + selectedImage.getImageWidth() && imageOffset.get(1).y == selectedImage.getY())
            {
                if (imageOffset.get(2).x == selectedImage.getX() + selectedImage.getImageWidth() && imageOffset.get(2).y == selectedImage.getY() + selectedImage.getImageHeight())
                {
                    if (imageOffset.get(3).x == selectedImage.getX() && imageOffset.get(3).y == selectedImage.getY() + selectedImage.getImageHeight())
                    {
                        return false;
                    }

                }

            }
        }
        return true;
    }

    public List<Vector2> getBoundaries (){
        calculateImageBoundaryHandles ();

        List<Vector2> result = new ArrayList<Vector2>();

        for (Image handle : boundaryHandles){
            result.add(new Vector2(handle.getX(), handle.getY()));
        }
        return result;
    }

    public List<Vector2> calculateImageBoundary() {

        calculateImageBoundaryHandles ();

        boundary.clear ();

        boundary.add(new Vector2(selectedImage.getX(), selectedImage.getY()));
        boundary.add(new Vector2(selectedImage.getX() + selectedImage.getImageWidth(), selectedImage.getY()));
        boundary.add(new Vector2(selectedImage.getX() + selectedImage.getImageWidth(), selectedImage.getY() + selectedImage.getImageHeight()));
        boundary.add(new Vector2(selectedImage.getX(), selectedImage.getY() + selectedImage.getImageHeight()));



        return boundary;
    }

    private void addHandlesToStage() {

        for (Image boundaryHandle : boundaryHandles) {

            stage.getStage().addActor(boundaryHandle);

        }
    }



    private void initialiseHandles() {

        boundaryHandles.clear();

        List<Vector2> collisionBoundaries = calculateImageBoundary();

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

                    System.out.println("Handle offset is " + im.getX() + ", " + im.getY());

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


