package com.example.pcuser.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final int PICKFILE_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView my_text_view = (TextView) findViewById(R.id.my_text_view);
        my_text_view.setSelected(true);

        final Button my_close_button = (Button) findViewById(R.id.my_close_button);
        my_close_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });



        final Button my_search_button = (Button) findViewById(R.id.my_search_button);
        my_search_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
            }
        });

        final Button my_convert_button = (Button) findViewById(R.id.my_convert_button);
        my_convert_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                TextView my_text_view = (TextView) findViewById(R.id.my_text_view);
                String file_path = my_text_view.getText().toString();
                if (!file_path.equals("Pick a prc file")) {
                    // Create a new file path with the extension .pdf
                    int idx = file_path.indexOf(".prc");
                    String new_file_path;
                    if (idx != -1) {
                        new_file_path = file_path.substring(0, idx) + ".epub";
                    } else {
                        new_file_path = file_path + ".epub";
                    }
                    // Read the file
                    File file = new File(file_path);
                    try {
                        int ret = ConvLib.mobiToEpubNative(file_path, new_file_path);
                        //Toast.makeText(getApplicationContext(), new_file_path,Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), Integer.toString(ret),Toast.LENGTH_LONG).show();
                    } catch(Exception e) {
                        Toast.makeText(getApplicationContext(), "Cannot convert the input file!",Toast.LENGTH_LONG).show();
                    }


                }



            }
        });

        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch(requestCode){
            case PICKFILE_REQUEST_CODE:

                if(resultCode==RESULT_OK){
                    String file_path = data.getData().getPath();
                    TextView my_text_view = (TextView) findViewById(R.id.my_text_view);
                    my_text_view.setText(file_path);
                }
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
