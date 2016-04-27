package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.io.File;

import static com.badlogic.gdx.scenes.scene2d.ui.Tree.*;

/**
 * Created by rhodges on 27/04/16.
 */
public class CommonFileDialog {

    private Dialog mainDialog;

    private String title = "Common File Dialog";

    private String currentPath = "/";

    private Skin skin;

    private Tree tree;

    public CommonFileDialog(Skin skin) {
        this.skin = skin;

        mainDialog = new Dialog(title,skin);
        mainDialog.setResizable(true);

        mainDialog.setModal(true);



        Table table = new Table();
        mainDialog.add(table);


        Label pathLabel = new Label("Path:", skin);

        TextField pathField = new TextField(currentPath, skin);

        TextButton pathButton = new TextButton("go", skin);

        table.add(pathLabel);
        table.add(pathField);
        table.add(pathButton);

        table.row();


        tree = new Tree(skin);

        final Table fileTable = new Table(skin);

        Table directoryTable = new Table (skin);

        ScrollPane fileScrollPane = new ScrollPane(directoryTable,skin);

        fileScrollPane.setScrollBarPositions(true, true);

        directoryTable.setFillParent(true);
        directoryTable.add(tree);
//        directoryTable.add(tree).height(200);


        refreshFileModel ();

        fileTable.add (fileScrollPane).height(200);
        fileTable.row();

//        fileScrollPane.setBounds(0,0,100,100);
        fileScrollPane.setTransform(true);
        fileScrollPane.setLayoutEnabled(true);


        table.add(fileTable).colspan(3);
    }

    private void refreshFileDisplay (){

        tree.clear();


    }

    private void refreshFileModel (){
        tree.clear();

        File dir = new File("/home/rhodges/Downloads");
        File[] filesList = dir.listFiles();

        for (File file : filesList) {
            if (file.isFile()) {
                final Node node = new Node(new Label(file.getName(), skin));
                tree.add(node);
            }
        }

    }


    public void show (Stage stage){
        mainDialog.show(stage);
    }

    public Dialog getMainDialog() {
        return mainDialog;
    }
}
