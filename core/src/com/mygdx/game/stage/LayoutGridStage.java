package com.mygdx.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.actor.GameObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhodges on 06/05/16.
 */
public class LayoutGridStage implements VirtualStage {

    private Stage stage;

    private NumberFormat nf = NumberFormat.getInstance();

    private Label midLabel;

    private Label topLabel;

    private Label botLabel;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private List<GameObject> gameObjectList = new ArrayList<GameObject>();

    @Override
    public void addVirtualSprite(GameObject gameObject) {
        stage.addActor(gameObject.getActor());

        if (!gameObjectList.contains(gameObjectList)){
            gameObjectList.add(gameObject);
            gameObject.actorAddedToStage();
        }
    }

    @Override
    public void renderStage() {
        // Render Background Grid
        this.renderGrid();

        // Render the Actors in the stage
        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());

        // Render Additional Information such as Physics
        // TODO

        renderBoundaries ();

    }

    public LayoutGridStage(Skin skin) {

        stage = new Stage(new StretchViewport(1024, 768));

        midLabel = new Label(nf.format(stage.getWidth() / 2) + "," + nf.format(stage.getHeight() / 2), skin);
        midLabel.setColor(Color.GRAY);

        topLabel = new Label(nf.format(stage.getWidth() / 2) + "," + nf.format(stage.getHeight()), skin);
        topLabel.setColor(Color.GRAY);

        botLabel = new Label(nf.format(stage.getWidth() / 2) + "," + 0, skin);
        botLabel.setColor(Color.GRAY);


        stage.addActor(midLabel);
        stage.addActor(topLabel);
        stage.addActor(botLabel);

        midLabel.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
        topLabel.setPosition(stage.getWidth() / 2, stage.getHeight() - topLabel.getHeight());
        botLabel.setPosition(stage.getWidth() / 2, 0);


        midLabel.setFontScale(.9f);
        topLabel.setFontScale(.9f);
        botLabel.setFontScale(.9f);
    }

    private void dottedLine( int dotDist, float x1, float y1, float x2, float y2) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);

        shapeRenderer.setColor(Color.DARK_GRAY);

        Vector2 vec2 = new Vector2(x2, y2).sub(new Vector2(x1, y1));
        float length = vec2.len();
        for(int i = 0; i < length; i += dotDist) {
            vec2.clamp(length - i, length - i);
            shapeRenderer.point(x1 + vec2.x, y1 + vec2.y, 0);
        }

        shapeRenderer.end();
    }

    private void renderGrid (){

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);

        shapeRenderer.line(stage.getWidth() / 2,0,stage.getWidth() / 2, stage.getHeight(), Color.GRAY, Color.GRAY);
        shapeRenderer.line(0, stage.getHeight() / 2, stage.getWidth(), stage.getHeight() / 2, Color.GRAY, Color.GRAY);

        shapeRenderer.end();

        dottedLine(5,(stage.getWidth() / 2) - 100, 0, (stage.getWidth() / 2) - 100, stage.getHeight());
        dottedLine(5,(stage.getWidth() / 2) - 200, 0, (stage.getWidth() / 2) - 200, stage.getHeight());
        dottedLine(5,(stage.getWidth() / 2) - 300, 0, (stage.getWidth() / 2) - 300, stage.getHeight());
        dottedLine(5,(stage.getWidth() / 2) - 400, 0, (stage.getWidth() / 2) - 400, stage.getHeight());
        dottedLine(5,(stage.getWidth() / 2) - 500, 0, (stage.getWidth() / 2) - 500, stage.getHeight());

        dottedLine(5,(stage.getWidth() / 2) + 100, 0, (stage.getWidth() / 2) + 100, stage.getHeight());
        dottedLine(5,(stage.getWidth() / 2) + 200, 0, (stage.getWidth() / 2) + 200, stage.getHeight());
        dottedLine(5,(stage.getWidth() / 2) + 300, 0, (stage.getWidth() / 2) + 300, stage.getHeight());
        dottedLine(5,(stage.getWidth() / 2) + 400, 0, (stage.getWidth() / 2) + 400, stage.getHeight());
        dottedLine(5,(stage.getWidth() / 2) + 500, 0, (stage.getWidth() / 2) + 500, stage.getHeight());

        dottedLine(5, 0, (stage.getHeight() / 2) - 100, stage.getWidth(), (stage.getHeight() / 2) - 100);
        dottedLine(5, 0, (stage.getHeight() / 2) - 200, stage.getWidth(), (stage.getHeight() / 2) - 200);
        dottedLine(5, 0, (stage.getHeight() / 2) - 300, stage.getWidth(), (stage.getHeight() / 2) - 300);
        dottedLine(5, 0, (stage.getHeight() / 2) - 400, stage.getWidth(), (stage.getHeight() / 2) - 400);
        dottedLine(5, 0, (stage.getHeight() / 2) - 500, stage.getWidth(), (stage.getHeight() / 2) - 500);

        dottedLine(5, 0, (stage.getHeight() / 2) + 100, stage.getWidth(), (stage.getHeight() / 2) + 100);
        dottedLine(5, 0, (stage.getHeight() / 2) + 200, stage.getWidth(), (stage.getHeight() / 2) + 200);
        dottedLine(5, 0, (stage.getHeight() / 2) + 300, stage.getWidth(), (stage.getHeight() / 2) + 300);
        dottedLine(5, 0, (stage.getHeight() / 2) + 400, stage.getWidth(), (stage.getHeight() / 2) + 400);
        dottedLine(5, 0, (stage.getHeight() / 2) + 500, stage.getWidth(), (stage.getHeight() / 2) + 500);

    }

    public void resize (){

        midLabel.setText(nf.format(stage.getWidth() / 2) + "," + nf.format(stage.getHeight() / 2));
        midLabel.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);

        topLabel.setText(stage.getWidth() / 2 + "," + stage.getHeight());
        topLabel.setPosition(stage.getWidth() / 2, stage.getHeight() - topLabel.getHeight());

        botLabel.setText(stage.getWidth() / 2 + "," + 0);
        botLabel.setPosition(stage.getWidth() / 2, 0);
    }

    private void renderBoundaries (){
        for (GameObject go : gameObjectList){

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            shapeRenderer.setColor(Color.GOLD);

            Vector2 start = null;
            Vector2 first = null;


            for (Vector2 point : go.collisionBoundaries()){

                if (start == null){
                    start = point;
                    first = point;
                } else {
                    shapeRenderer.line(start, point);
                    start = point;
                }
            }

            // Close shape
            shapeRenderer.line(start, first);


            shapeRenderer.end();
        }
    }

    public Stage getStage() {
        return stage;
    }
}
