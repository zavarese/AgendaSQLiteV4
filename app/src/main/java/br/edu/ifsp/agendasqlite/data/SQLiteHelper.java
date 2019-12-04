package br.edu.ifsp.agendasqlite.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "agenda.db";
    static final String TABLE_NAME ="contatos";

    static final String KEY_ID = "id";
    static final String KEY_NOME = "nome";
    static final String KEY_FONE = "fone";
    static final String KEY_EMAIL = "email";
    static final String KEY_FAVORITO = "favorito";
    static final String KEY_FONE_2 = "fone_2";
    static final String KEY_NASCIMENTO = "nascimento";


    private static final int DATABASE_VERSION = 4;

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                                               + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                               + KEY_NOME + " TEXT, "
                                               + KEY_FONE + " TEXT, "
                                               + KEY_EMAIL + " TEXT);" ;

    private static final String DATABASE_ALTER_1 = "ALTER TABLE "
            + TABLE_NAME + " ADD COLUMN " + KEY_FAVORITO + " TEXT;";

    private static final String DATABASE_ALTER_2 = "ALTER TABLE "
            + TABLE_NAME + " ADD COLUMN " + KEY_FONE_2 + " TEXT;";

    private static final String DATABASE_ALTER_3 = "ALTER TABLE "
            + TABLE_NAME + " ADD COLUMN " + KEY_NASCIMENTO + " TEXT;";


    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL(DATABASE_ALTER_1);
        }

        if (oldVersion < 3) {
            db.execSQL(DATABASE_ALTER_2);
        }

        if (oldVersion < 4) {
            db.execSQL(DATABASE_ALTER_3);
        }
    }
}
