package br.edu.ifspsaocarlos.controlebancario.dao;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "controlebancario.db";
    static final String DATABASE_TABLE = "conta";
    static final String KEY_ID = "id";
    static final String KEY_DESC = "descricao";
    static final String KEY_VALOR = "valor";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "CREATE TABLE "+ DATABASE_TABLE +" (" +
            KEY_ID  +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DESC + " TEXT NOT NULL, " +
            KEY_VALOR + " DOUBLE); ";

    private static final String DATABASE_CREATE2 = "CREATE TABLE if not exists transacao (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "tipo TEXT NOT NULL, " +
            "valor DOUBLE, " +
            "conta TEXT, " +
            "periodo TEXT, " +
            "repeticao INT, " +
            "repetido INT, " +
            "data DATE); ";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE2);
    }



    @Override
    public void onUpgrade(SQLiteDatabase databse, int oldVersion, int    newVersion) {

    }
}

