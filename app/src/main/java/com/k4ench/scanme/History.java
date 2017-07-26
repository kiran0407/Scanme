package com.k4ench.scanme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dd.processbutton.iml.SubmitProcessButton;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class History extends AppCompatActivity implements ProgressGenerator.OnCompleteListener {
    private ListView lv;
    private DBHelper dbHelper;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private String calcName="";
    private String []EmptyList={"There is  no history yet"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upIntent = NavUtils.getParentActivityIntent(History.this);
                upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(History.this, upIntent);
            }
        });
        final ProgressGenerator progressGenerator = new ProgressGenerator(this);
        final SubmitProcessButton btnDel = (SubmitProcessButton) findViewById(R.id.deleteHistory);
        lv = (ListView) findViewById(R.id.listView);
        dbHelper = new DBHelper(this);
        calcName = getIntent().getStringExtra("calcName");
        list = dbHelper.showHistory(calcName);
        if (!list.isEmpty())
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        else
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, EmptyList);
        lv.setAdapter(adapter);

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String inp=editMessage.getText().toString();

                    progressGenerator.start(btnDel);
                    btnDel.setEnabled(false);
                dbHelper.deleteRecords(calcName);
                adapter=new ArrayAdapter<String>(History.this,android.R.layout.simple_list_item_1,EmptyList);
                lv.setAdapter(adapter);

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onComplete() {
        TastyToast.makeText(getApplicationContext(), "Deleted Successful !", TastyToast.LENGTH_LONG,
                TastyToast.SUCCESS);
    }
  /*  public void onClick(View v)
    {
        dbHelper.deleteRecords(calcName);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,EmptyList);
        lv.setAdapter(adapter);
    }*/
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event)  {
      if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
              && keyCode == KeyEvent.KEYCODE_BACK
              && event.getRepeatCount() == 0) {
          Log.d("CDA", "onKeyDown Called");
          onBackPressed();
          return true;
      }
      return super.onKeyDown(keyCode, event);
  }
}
