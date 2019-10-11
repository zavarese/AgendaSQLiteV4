package br.edu.ifsp.agendasqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.edu.ifsp.agendasqlite.model.Contato;

public class ContatoDAO {

    SQLiteDatabase database;
    SQLiteHelper dbHelper;

    public ContatoDAO(Context context)
    {
        this.dbHelper = new SQLiteHelper(context);
    }

    public void incluirContato (Contato c)
    {
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_NOME, c.getNome());
        values.put(SQLiteHelper.KEY_FONE, c.getFone());
        values.put(SQLiteHelper.KEY_EMAIL, c.getEmail());

        database.insert(SQLiteHelper.TABLE_NAME, null, values);

        database.close();

    }

}
