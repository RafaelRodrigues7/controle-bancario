package br.edu.ifspsaocarlos.controlebancario.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.controlebancario.R;
import br.edu.ifspsaocarlos.controlebancario.dao.*;

public class MainActivity extends AppCompatActivity {

    private ImageButton imgBtAddAcc;
    private ImageButton imgBtAddTrans;
    private ImageButton showAccImgBt;
    private ImageButton showTransImgBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initComponents();
    }

    private void initComponents(){

        DAO d = new DAO();
        try{
            d.selectAndUpdateTrans(getApplication());
        }
        catch(Exception e){
            e.printStackTrace();
        }

       //d.selectTrans(getApplication());
        this.imgBtAddAcc = (ImageButton) findViewById(R.id.imgBtAddAcc);
        this.imgBtAddTrans = (ImageButton) findViewById(R.id.imgBtAddTrans);
        this.showAccImgBt = (ImageButton) findViewById(R.id.showAccImgBt);
        this.showTransImgBt = (ImageButton) findViewById(R.id.showTransImgBt);

        this.imgBtAddTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), AddTransActivity.class);
                startActivityForResult(i, 1);
            }
        });

        this.imgBtAddAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), AddAccActivity.class);
                startActivityForResult(i, 1);
            }
        });

        this.showAccImgBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), ShowAccActivity.class);
                startActivityForResult(i, 1);
            }
        });

        this.showTransImgBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), ShowTransActivity.class);
                startActivityForResult(i, 1);
            }
        });
    }
}
