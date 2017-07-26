package com.k4ench.scanme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import static com.k4ench.scanme.BuilderManager.getImageResource1;

public class MainActivity1 extends AppCompatActivity {
    int MY_REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar mActionBar = getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View actionBar = mInflater.inflate(R.layout.custom_actionbar, null);
        mActionBar.setCustomView(actionBar);
        mActionBar.setDisplayShowCustomEnabled(true);
        ((Toolbar) actionBar.getParent()).setContentInsetsAbsolute(0,0);

        final BoomMenuButton rightBmb = (BoomMenuButton) actionBar.findViewById(R.id.bmb);


        rightBmb.setButtonEnum(ButtonEnum.Ham);
        rightBmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_1);
        rightBmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_1);

        for (int i = 0; i < rightBmb.getPiecePlaceEnum().pieceNumber(); i++) {

            HamButton.Builder builder = new HamButton.Builder()

                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if(index==0)
                            {
                                Intent i1=new Intent(MainActivity1.this,About1.class);
                                startActivity(i1);
                            }
                        }
                    }) .normalImageRes(R.mipmap.user).normalTextRes(getImageResource1());


            rightBmb.addBuilder(builder);
        }

        CircleMenu circleMenu = (CircleMenu) findViewById(R.id.circleMenu);
        circleMenu.setOnItemClickListener(new CircleMenu.OnItemClickListener() {
            @Override
            public void onItemClick(CircleMenuButton menuButton) {
                String msg="hello";
                switch (menuButton.getId()) {
                    case R.id.scan:
                        Intent i1=new Intent(MainActivity1.this,ScanActivity.class);
                        startActivity(i1);
                        break;
                    case R.id.replay:
                        Intent i2=new Intent(MainActivity1.this,SplashActivity.class);
                        i2.putExtra("btn",msg);
                        startActivity(i2);
                        break;
                    case R.id.history:
                        Intent i3=new Intent(MainActivity1.this,History.class);
                        i3.putExtra("calcName", "SCIENTIFIC");
                        startActivity(i3);
                       // showMssage("Alert");
                        break;
                    case R.id.feedback:
                        Intent i4=new Intent(MainActivity1.this,MessageActivity1.class);
                        startActivity(i4);
                        break;
                    case R.id.about:
                        Intent i5=new Intent(MainActivity1.this,About1.class);
                        startActivity(i5);
                        break;
                }
            }
        });

        circleMenu.setStateUpdateListener(new CircleMenu.OnStateUpdateListener() {
            @Override
            public void onMenuExpanded() {
                Log.d("CircleMenuStatus", "Expanded");
            }

            @Override
            public void onMenuCollapsed() {
                Log.d("CircleMenuStatus", "Collapsed");
            }
        });


    }

    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity1.this);
        builder.setTitle("EXIT");
        builder.setMessage("Do you want to exit?");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

            }
        });
        AlertDialog alert=builder.create();
        alert.show();


    }
}
