package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    EditText etEditText;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        populateArrayItems();
        lvItems.setAdapter(itemsAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        //////////////////////////////////////////////////////////////////////////////////
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this,EditItemActivity.class);
                i.putExtra("textItem",items.get(position).toString());
                pos = position;
                startActivityForResult(i,2);
            }
        });
    }


    public void populateArrayItems(){
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,items);
    }

    public void onAddItem(View view) {
        itemsAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }

    private  void readItems(){
       File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(file));
        }
        catch (IOException e){
            System.out.println("message File Not Found"+e);
        }
    }

    private  void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try{
           FileUtils.writeLines(file,items);
        }
        catch (IOException e){

        }
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2){
            String editName=data.getStringExtra("editItem").toString();
            items.set(pos,editName);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }

    }
}
