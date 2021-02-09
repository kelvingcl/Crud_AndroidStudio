package com.example.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText edtxt_code,edtxt_description,edtxt_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtxt_code=(EditText)findViewById(R.id.edtxt_code);
        edtxt_description=(EditText)findViewById(R.id.edtxt_description);
        edtxt_price=(EditText)findViewById(R.id.edtxt_price);
    }



    public void Registrar(View vw){
        AdminSQLiteOpenHelper objAdminSQLite = new AdminSQLiteOpenHelper(this,"Administracion",null,1);
        SQLiteDatabase objBaseDeDatos = objAdminSQLite.getWritableDatabase();

        String code = edtxt_code.getText().toString();
        String description = edtxt_description.getText().toString();
        String price = edtxt_price.getText().toString();

        if (!code.isEmpty()){
            Cursor objCursor = objBaseDeDatos.rawQuery("select * from articulos where codigo="+code,null);

            if (objCursor.moveToFirst()){
                Toast.makeText(this,"El artículo ya existe.",Toast.LENGTH_SHORT).show();
                edtxt_price.setText("");
                edtxt_code.setText("");
                edtxt_description.setText("");

            }else{

                if(!code.isEmpty() && !description.isEmpty() && ! price.isEmpty()){
                    ContentValues objregistro = new ContentValues();
                    objregistro.put("codigo",code);
                    objregistro.put("descripcion",description);
                    objregistro.put("precio",price);
                    objBaseDeDatos.insert("articulos",null,objregistro);
                    edtxt_code.setText("");
                    edtxt_description.setText("");
                    edtxt_price.setText("");
                    Toast.makeText(this,"Registro exitoso",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"Llene los campos por favor.",Toast.LENGTH_SHORT).show();
                }

            }

        }

    }

    public void Buscar(View vw){
        AdminSQLiteOpenHelper objAdminSQLite = new AdminSQLiteOpenHelper(this,"Administracion",null,1);
        SQLiteDatabase objBaseDeDatos = objAdminSQLite.getWritableDatabase();

        String code = edtxt_code.getText().toString();

        if(!code.isEmpty()){
            Cursor objCursor = objBaseDeDatos.rawQuery("select descripcion, precio from articulos where codigo="+code,null);

            if (objCursor.moveToFirst()){
                edtxt_description.setText(objCursor.getString(0));
                edtxt_price.setText(objCursor.getString(1));
                objBaseDeDatos.close();
            }else {
                Toast.makeText(this,"El artículo no existe.",Toast.LENGTH_SHORT).show();
                objBaseDeDatos.close();
            }

        }else {
            Toast.makeText(this,"Ingrese el código del artículo que desea buscar.",Toast.LENGTH_SHORT).show();
        }
    }

    public void Eliminar(View vw){
        AdminSQLiteOpenHelper objAdminSQLite = new AdminSQLiteOpenHelper(this,"Administracion",null,1);
        SQLiteDatabase objBaseDeDatos = objAdminSQLite.getWritableDatabase();
        String code = edtxt_code.getText().toString();

        if (!code.isEmpty()){
            int numReg = objBaseDeDatos.delete("articulos","codigo="+code,null);
            objBaseDeDatos.close();

            edtxt_price.setText("");
            edtxt_code.setText("");
            edtxt_description.setText("");

            if (numReg==1){
                Toast.makeText(this,"Registro eliminado exitosamente",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"El registro no existe",Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this,"Ingrese el código del registro que desea eliminar.",Toast.LENGTH_SHORT).show();
        }
    }

    public void Actualizar(View vw){
        AdminSQLiteOpenHelper objAdminSQLite = new AdminSQLiteOpenHelper(this,"Administracion",null,1);
        SQLiteDatabase objBaseDeDatos = objAdminSQLite.getWritableDatabase();

        String codigo= edtxt_code.getText().toString();
        String description = edtxt_description.getText().toString();
        String price = edtxt_price.getText().toString();

        if (!codigo.isEmpty() && !description.isEmpty() && !price.isEmpty()){

            ContentValues valores = new ContentValues();
            valores.put("codigo",codigo);
            valores.put("descripcion",description);
            valores.put("precio",price);

            int numReg = objBaseDeDatos.update("articulos",valores,"codigo="+codigo,null);

            edtxt_price.setText("");
            edtxt_code.setText("");
            edtxt_description.setText("");

            objBaseDeDatos.close();
            if (numReg==1){
                Toast.makeText(this,"Registro modificado exitosamente.",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"El registro no existe.", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this,"Ingrese los datos del registro que desea modificar.",Toast.LENGTH_SHORT).show();
        }
    }
}