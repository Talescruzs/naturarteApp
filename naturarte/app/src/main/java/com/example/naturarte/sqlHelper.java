package com.example.naturarte;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class sqlHelper extends SQLiteOpenHelper {

    private static final String db_Name = "registros.db";
    private static final int db_Version = 1;

    private static sqlHelper instance;

    public static sqlHelper getInstance(Context context){
        if (instance==null)
            instance=new sqlHelper(context);
        return instance;
    }

    public sqlHelper(@Nullable Context context) { //construtor da classe (deixar nessa estrutura utilizando as variavies db_Name e db_version, Factory nao precisa)
        super(context, db_Name, null, db_Version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "- TABLE registros (id INTEGER primary key autoincrement, tipo TEXT not null, nome TEXT not null, especialidade TEXT, salario FLOAT)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @SuppressLint("Range")
    public List<Registro> getRegistro(String valor){
        Cursor cursor;

        List<Registro> registros = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        if(valor.isEmpty()){
            cursor = db.rawQuery("Select * from registros",null);
        }
        else{
            cursor = db.rawQuery("Select * from registros where disciplina = ?",new String[]{valor});
        }
        try{
            if(cursor.moveToFirst()){
                do{
                    Registro registro = new Registro();
                    registro.tipo = cursor.getString(cursor.getColumnIndex("tipo"));
                    registro.nome = cursor.getString(cursor.getColumnIndex("nome"));
                    registro.especialidade = cursor.getString(cursor.getColumnIndex("especialidade"));
                    registro.salario = cursor.getFloat(cursor.getColumnIndex("salario"));

                    registros.add(registro);
                }while(cursor.moveToNext());
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null && !cursor.isClosed())
            cursor.close();
        }
        return registros;
    }

    public long addRegistro(String tipo, String nome, String especialidade, Float salario){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long id_table = 0;
        try{
          sqLiteDatabase.beginTransaction();
            ContentValues valores = new ContentValues();
            valores.put("tipo", tipo);
            valores.put("nome",nome);
            valores.put("especialidade",especialidade);
            valores.put("salario",salario);
            id_table = sqLiteDatabase.insertOrThrow("registros",null,valores);
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e){
            Log.e("sqllite",e.getMessage(),e);

        } finally {
            sqLiteDatabase.endTransaction();
        }
        return id_table;

    }

    public void removeRegistro(String tipo, String nome, String especialidade, Float salario){
        SQLiteDatabase db = getWritableDatabase();

        try{
            db.execSQL("delete from registros where tipo = ? and nome = ? and especialidade = ? and salario = ?",new String[]{tipo, nome, especialidade, String.valueOf(salario)});
        } catch (Exception e){
            Log.e("sqllite",e.getMessage(),e);
        }
    }

}
