package br.edu.ifspsaocarlos.agenda.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lucas on 08/11/16.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "agenda.db";

    public static final String DATABASE_TABLE = "contatos";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "nome";
    public static final String KEY_FONE = "fone";
    public static final String KEY_FONE2 = "fone2";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_DIA = "dia";
    public static final String KEY_MES = "mes";

    public static final int DATABASE_VERSION = 3;

    public static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE +
            " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NAME + " TEXT NOT NULL," +
                KEY_FONE + " TEXT," +
                KEY_FONE2 + " TEXT," +
                KEY_DIA + " TEXT," +
                KEY_MES + " TEXT," +
                KEY_EMAIL + " TEXT);";

    public static final String INCLUDE_FONE2 = "ALTER TABLE " + DATABASE_TABLE +
                " ADD COLUMN " + KEY_FONE2 + " TEXT";

    public static final String INCLUDE_DIA_ANIVERSARIO = "ALTER TABLE " + DATABASE_TABLE +
                " ADD COLUMN " + KEY_DIA + " TEXT";
    public static final String INCLUDE_MES_ANIVERSARIO = "ALTER TABLE " + DATABASE_TABLE +
            " ADD COLUMN " + KEY_MES + " TEXT";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        if(oldVersion == 1 && newVersion == 2) {
            database.execSQL(INCLUDE_FONE2);
        }
        if(oldVersion == 2 && newVersion == 3) {
            database.execSQL(INCLUDE_DIA_ANIVERSARIO);
            database.execSQL(INCLUDE_MES_ANIVERSARIO);
        }
        if(oldVersion == 1 && newVersion == 3) {
            database.execSQL(INCLUDE_FONE2);
            database.execSQL(INCLUDE_DIA_ANIVERSARIO);
            database.execSQL(INCLUDE_MES_ANIVERSARIO);
        }
    }
}
