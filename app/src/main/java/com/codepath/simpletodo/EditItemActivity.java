package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    public int position;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String  textItem = getIntent().getStringExtra("textItem");
        editText = (EditText) findViewById(R.id.editText);
        editText.setText(textItem);

    }

    public void onSave(View view) {
        Intent i = new Intent();
        i.putExtra("editItem",editText.getText().toString());
        setResult(2,i);
        finish();
    }
}
