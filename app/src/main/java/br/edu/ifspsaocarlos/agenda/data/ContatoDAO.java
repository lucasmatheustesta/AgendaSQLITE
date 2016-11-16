package br.edu.ifspsaocarlos.agenda.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.agenda.model.Contato;

/**
 * Created by lucas on 08/11/16.
 */

public class ContatoDAO {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public ContatoDAO(Context context) {
        this.dbHelper = new SQLiteHelper(context);
    }


    public List<Contato> buscaContato(String nomeOuEmail) {
        database = dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();
        Cursor cursor;

        String[] cols = new String[]{SQLiteHelper.KEY_ID, SQLiteHelper.KEY_NAME, SQLiteHelper.KEY_FONE, SQLiteHelper.KEY_EMAIL, SQLiteHelper.KEY_FONE2,
                                    SQLiteHelper.KEY_DIA, SQLiteHelper.KEY_MES};
        String where = null;
        String[] argWhere = null;

        if(nomeOuEmail != null) {
            where = SQLiteHelper.KEY_NAME + " LIKE ? OR " + SQLiteHelper.KEY_EMAIL + " LIKE ?";
            argWhere = new String[]{nomeOuEmail + "%", nomeOuEmail + "%"};
        }

        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, where, argWhere, null, null, SQLiteHelper.KEY_NAME);

        if(cursor != null) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Contato contato = new Contato();
                contato.setId(cursor.getInt(0));
                contato.setNome(cursor.getString(1));
                contato.setFone(cursor.getString(2));
                contato.setEmail(cursor.getString(3));
                contato.setFone2(cursor.getString(4));
                contato.setDia_aniversario(cursor.getString(5));
                contato.setMes_aniversario(cursor.getString(6));
                contatos.add(contato);
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();
        return contatos;
    }

    public void atualizaContato(Contato c) {
        database = dbHelper.getWritableDatabase();
        ContentValues updateValues = new ContentValues();
        updateValues.put(SQLiteHelper.KEY_NAME, c.getNome());
        updateValues.put(SQLiteHelper.KEY_FONE, c.getFone());
        updateValues.put(SQLiteHelper.KEY_FONE2, c.getFone2());
        updateValues.put(SQLiteHelper.KEY_EMAIL, c.getEmail());
        updateValues.put(SQLiteHelper.KEY_DIA, c.getDia_aniversario());
        updateValues.put(SQLiteHelper.KEY_MES, c.getMes_aniversario());
        database.update(SQLiteHelper.DATABASE_TABLE, updateValues, SQLiteHelper.KEY_ID + "= "+ c.getId(), null);
        database.close();
    }

    public void insereContato(Contato c) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_NAME, c.getNome());
        values.put(SQLiteHelper.KEY_FONE, c.getFone());
        values.put(SQLiteHelper.KEY_EMAIL, c.getEmail());
        values.put(SQLiteHelper.KEY_FONE2, c.getFone2());
        values.put(SQLiteHelper.KEY_DIA, c.getDia_aniversario());
        values.put(SQLiteHelper.KEY_MES, c.getMes_aniversario());
        database.insert(SQLiteHelper.DATABASE_TABLE, null, values);
        database.close();
    }

    public void apagaContato(Contato c) {
        database = dbHelper.getWritableDatabase();
        database.delete(SQLiteHelper.DATABASE_TABLE, SQLiteHelper.KEY_ID + "=" + c.getId(), null);
        database.close();
    }
}
