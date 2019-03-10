package com.example.acer.employeelist;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditSingleRecordActivity extends AppCompatActivity {

    EditText name, phone_number;
    Button update;
    SQLiteDatabase sqLiteDatabase;
    SQLHelper SQLHelper;
    Cursor cursor;
    String IDholder;
    String SQLiteDataBaseQueryHolder ;
    SQLiteDatabase sqLiteDatabaseObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_single_record);

        name = (EditText) findViewById(R.id.EditTextName);
        phone_number = (EditText) findViewById(R.id.editText3);

        update = (Button) findViewById(R.id.buttonUpdate);

        SQLHelper = new SQLHelper(this);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String GetName = name.getText().toString();
                String GetPhoneNumber = phone_number.getText().toString();

                OpenSQLiteDataBase();

                SQLiteDataBaseQueryHolder = "UPDATE " + SQLHelper.TABLE_NAME + " SET "+ SQLHelper.Table_Column_1_Name+" = '"+GetName+"' , "+ SQLHelper.Table_Column_2_PhoneNumber+" = '"+GetPhoneNumber+"' WHERE id = " + IDholder + "";

                sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

                sqLiteDatabase.close();

                Toast.makeText(EditSingleRecordActivity.this,"Data Edit Successfully", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onResume() {

        ShowSRecordInEditText();

        super.onResume();
    }

    public void ShowSRecordInEditText() {

        sqLiteDatabase = SQLHelper.getWritableDatabase();

        IDholder = getIntent().getStringExtra("EditID");

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLHelper.TABLE_NAME + " WHERE id = " + IDholder + "", null);

        if (cursor.moveToFirst()) {

            do {
                name.setText(cursor.getString(cursor.getColumnIndex(SQLHelper.Table_Column_1_Name)));

                phone_number.setText(cursor.getString(cursor.getColumnIndex(SQLHelper.Table_Column_2_PhoneNumber)));
            }
            while (cursor.moveToNext());

            cursor.close();

        }
    }

    public void OpenSQLiteDataBase(){

        sqLiteDatabaseObj = openOrCreateDatabase(SQLHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

}