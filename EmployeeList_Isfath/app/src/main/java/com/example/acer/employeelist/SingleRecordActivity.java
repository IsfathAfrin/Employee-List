package com.example.acer.employeelist;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SingleRecordActivity extends AppCompatActivity {

    String IDholder;
    TextView id, name, phone_number;
    SQLiteDatabase sqLiteDatabase;
    SQLHelper SQLHelper;
    Cursor cursor;
    Button Delete, Edit;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_record);

        id = (TextView) findViewById(R.id.textViewID);
        name = (TextView) findViewById(R.id.textViewName);
        phone_number = (TextView) findViewById(R.id.textViewPhoneNumber);

        Delete = (Button)findViewById(R.id.buttonDelete);
        Edit = (Button)findViewById(R.id.buttonEdit);

        SQLHelper = new SQLHelper(this);

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenSQLiteDataBase();

                SQLiteDataBaseQueryHolder = "DELETE FROM "+ SQLHelper.TABLE_NAME+" WHERE id = "+IDholder+"";

                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);

                sqLiteDatabase.close();

                finish();

            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),EditSingleRecordActivity.class);

                intent.putExtra("EditID", IDholder);

                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {

        ShowSingleRecordInTextView();

        super.onResume();
    }

    public void ShowSingleRecordInTextView() {

        sqLiteDatabase = SQLHelper.getWritableDatabase();

        IDholder = getIntent().getStringExtra("ListViewClickedItemValue");

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLHelper.TABLE_NAME + " WHERE id = " + IDholder + "", null);

        if (cursor.moveToFirst()) {

            do {
                id.setText(cursor.getString(cursor.getColumnIndex(SQLHelper.Table_Column_ID)));
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