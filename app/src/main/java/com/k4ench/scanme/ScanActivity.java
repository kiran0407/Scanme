package com.k4ench.scanme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.microblink.activity.SegmentScanActivity;
import com.microblink.ocr.ScanConfiguration;
import com.microblink.recognizers.blinkocr.parser.generic.RawParserSettings;

public class ScanActivity extends AppCompatActivity {
    int MY_REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = new Intent(this, SegmentScanActivity.class);
        intent.putExtra(SegmentScanActivity.EXTRAS_LICENSE_KEY, "5T5ZGLBW-ELBCKCNN-D2KW5PHO-HRGNOYUI-W2VF6QHZ-JJSK7M42-6MLGCQS3-4EH4O4EE");

        ScanConfiguration[] confArray = new ScanConfiguration[] {
                new ScanConfiguration(R.string.raw_title, R.string.raw_msg, "Raw", new RawParserSettings())
        };
        intent.putExtra(SegmentScanActivity.EXTRAS_SCAN_CONFIGURATION, confArray);

// Starting Activity
        startActivityForResult(intent, MY_REQUEST_CODE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode == SegmentScanActivity.RESULT_OK && data != null) {
                Bundle extras = data.getExtras();
                Bundle results = extras.getBundle(SegmentScanActivity.EXTRAS_SCAN_RESULTS);
                String data1 = results.getString("Raw");
                Intent intent =new Intent(ScanActivity.this,ScanMeRanguski.class);
                intent.putExtra("data",data1);
                startActivity(intent);
            }
        }
    }

    public void onBackPressed() {
      finish();
    }

}
