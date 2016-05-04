package com.mygdx.game.dialog;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.List;

import java.io.File;
import java.util.ArrayList;



/**
 * Created by rhodges on 27/04/16.
 */
public class CommonFileDialog {

    private final Dialog mainDialog;

    private String title = "Common File Dialog";

    private String currentPath = "/home/rhodges/Downloads";

    private Skin skin;

    private List displayList;

    private ArrayList<String> items;

    private TextButton cancelButton;

    private CommonFileListener listener;



    public CommonFileDialog(Skin skin, final CommonFileListener listener) {
        this.skin = skin;
        this.listener = listener;

        mainDialog = new Dialog(title, skin);

        Table table = new Table();
        mainDialog.add(table);

        Label pathLabel = new Label("Path:", skin);

        final TextField pathField = new TextField(currentPath, skin);

        TextButton pathButton = new TextButton("go", skin);

        TextButton loadButton = new TextButton("load", skin);

        cancelButton = new TextButton("cancel", skin);

        table.add(pathLabel);
        table.add(pathField).width(370);
        table.add(pathButton);

        table.row().pad(20);

        displayList = new List(skin);
        refreshFileModel();


        final Table fileTable = new Table(skin);

        Table directoryTable = new Table(skin);
        ScrollPane fileScrollPane = new ScrollPane(directoryTable, skin);
        directoryTable.add(displayList);

        fileTable.add(fileScrollPane).height(200);
        fileTable.row();


        table.add(fileTable).colspan(3);

        table.row();

        table.add(cancelButton).colspan(2).right().width(100);
        table.add(loadButton).right().width(100);

        cancelButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {



                mainDialog.hide();

                return true;

            }
        });

        loadButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                listener.loadFile( pathField.getText() + "/" + displayList.getSelected() );

                return super.touchDown(event, x, y, pointer, button);
            }
        });

    }

    private void refreshFileModel (){

        items = new ArrayList<String>();

        File dir = new File(currentPath);
        File[] filesList = dir.listFiles();

        for (File file : filesList) {
            if (file.isFile() && file.getName().trim().length() > 1) {
                items.add(file.getName().trim());
            }
        }
        displayList.setItems(items.toArray());


    }


    public void show (Stage stage){
        mainDialog.show(stage);
    }

    public Dialog getMainDialog() {
        return mainDialog;
    }

}
