package com.k4ench.scanme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.androidessence.pinchzoomtextview.PinchZoomTextView;
import com.sdsmdg.tastytoast.TastyToast;

import java.net.URLEncoder;
import java.util.Stack;

public class ScanMeRanguski extends AppCompatActivity {
    TextView tex;
    String input;
    private DBHelper dbHelper;
    int fact1 = 1, fact2 = 1,r=1;
    Animation a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide statusbar of Android
        // could also be done later
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scan_me_ranguski);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(android.os.Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        dbHelper=new DBHelper(this);
        setTitle("Result");
        //res=(TextView) findViewById(scanedme_timed);
         PinchZoomTextView res = (PinchZoomTextView) findViewById(R.id.scanedme_timed);
        tex=(TextView) findViewById(R.id.textView);
        a = AnimationUtils.loadAnimation(this, R.anim.anima);
        tex.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                tex.startAnimation(a);
            }});



    Intent intent=getIntent();
         input = intent.getStringExtra("data");
        res.setZoomEnabled(true);
        //res.setText(input);
        input=input.trim();
        try {
            if (input.contains("*") || input.contains("+") || input.contains("/") || input.contains("-") || input.contains("%")) {
                input = input.replaceAll("[^0-9*/+)(%!-]", "");

                //Scanner scan = new Scanner(System.in);
        /* Create stacks for operators and operands */
                Stack<Integer> op = new Stack<Integer>();
                Stack<Double> val = new Stack<Double>();
        /* Create temporary stacks for operators and operands */
                Stack<Integer> optmp = new Stack<Integer>();
                Stack<Double> valtmp = new Stack<Double>();
        /* Accept expression */
                /*System.out.println("Evaluation Of Arithmetic Expression Using Stacks Test\n");
                System.out.println("Enter expression\n");*/
                // String input = input1.getText().toString();
                input = "0" + input;
                input = input.replaceAll("-", "+-");
        /* Store operands and operators in respective stacks */
                String temp = "";
                for (int i = 0; i < input.length(); i++) {
                    char ch = input.charAt(i);
                    if (ch == '-')
                        temp = "-" + temp;
                    else if (ch != '+' && ch != '*' && ch != '/' && ch != '%')
                        temp = temp + ch;
                    else {
                        val.push(Double.parseDouble(temp));
                        op.push((int) ch);
                        temp = "";
                    }
                }


                val.push(Double.parseDouble(temp));
        /* Create char array of operators as per precedence */
        /* -ve sign is already taken care of while storing */
                char operators[] = {'/', '*', '+','%'};
        /* Evaluation of expression */
                for (int i = 0; i < 4; i++) {
                    boolean it = false;
                    while (!op.isEmpty()) {
                        int optr = op.pop();
                        double v1 = val.pop();
                        double v2 = val.pop();
                        if (optr == operators[i]) {
                    /* if operator matches evaluate and store in temporary stack */
                            if (i == 0) {
                                valtmp.push(v2 / v1);
                                it = true;
                                break;
                            } else if (i == 1) {
                                valtmp.push(v2 * v1);
                                it = true;
                                break;
                            } else if (i == 2) {
                                valtmp.push(v2 + v1);
                                it = true;
                                break;
                            } else if (i == 3) {
                                valtmp.push(v2 % v1);
                                it = true;
                                break;
                            }
                        } else {
                            valtmp.push(v1);
                            val.push(v2);
                            optmp.push(optr);
                        }
                    }
            /* Push back all elements from temporary stacks to main stacks */
                    while (!valtmp.isEmpty())
                        val.push(valtmp.pop());
                    while (!optmp.isEmpty())
                        op.push(optmp.pop());
            /* Iterate again for same operator */
                    if (it)
                        i--;
                }
                res.setText("\nResult = " + val.pop());
                String arth=res.getText().toString();
                dbHelper.insert("SCIENTIFIC", input + " = " + arth);
            }
            else if (input.contains("p") || input.contains("P")) {
                input = input.replaceAll("[^pP0-9]", "");
                String[] perm = input.split("p|P");

                for (int i = 0; i < perm.length; i++) {
                    if (Integer.parseInt(perm[1]) > Integer.parseInt(perm[0])) {
                        TastyToast.makeText(getApplicationContext(), " Please enter proper text", TastyToast.LENGTH_LONG,
                                TastyToast.INFO);
                    } else {
                        int sec = Integer.parseInt(perm[0]) - Integer.parseInt(perm[1]);
                        int num = Integer.parseInt(perm[0]);
                        for (i = 1; i <= num; i++) {
                            fact1 = fact1 * i;
                        }
                        for (i = 1; i <= sec; i++) {
                            fact2 = fact2 * i;
                        }
                        int result = fact1 / fact2;
                        res.setText(Integer.toString(result));
                        String arth=res.getText().toString();
                        dbHelper.insert("SCIENTIFIC", input + " = " + arth);
                    }
                }
            }
            else if (input.contains("c") || input.contains("C")) {
                input = input.replaceAll("[^cC0-9]", "");
                String[] perm = input.split("c|C");
                for (int i = 0; i < perm.length; i++) {
                    // Toast.makeText(MainActivity.this, perm[i], Toast.LENGTH_LONG).show();
                    if (Integer.parseInt(perm[1]) > Integer.parseInt(perm[0])) {
                        TastyToast.makeText(getApplicationContext(), " Please enter proper text", TastyToast.LENGTH_LONG,
                                TastyToast.INFO);
                    } else {
                        int sec = Integer.parseInt(perm[0]) - Integer.parseInt(perm[1]);
                        int num = Integer.parseInt(perm[0]);
                        int num1 = Integer.parseInt(perm[1]);
                        for (i = 1; i <= num; i++) {
                            fact1 = fact1 * i;
                        }
                        for (i = 1; i <= num1; i++) {
                            r = r * i;
                        }
                        for (i = 1; i <= sec; i++) {
                            fact2 = fact2 * i;
                        }
                        int com = fact2 * r;

                        int result = fact1 / com;
                        res.setText(Integer.toString(result));
                        String arth=res.getText().toString();
                        dbHelper.insert("SCIENTIFIC", input + " = " + arth);
                    }
                }
            }
            else if(input.contains(".*[^a-z].*")){
                input = input.replaceAll("[^a-z]", "");
                String query = URLEncoder.encode(input, "utf-8");
                String url = "http://www.google.com/search?q=" + query;
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse(url));
                startActivity(intent1);
            }else if (input.contains("!")) {
                input = input.replaceAll("[^!0-9]", "");

                int fact1 = 1;
                String rep=input.replaceAll("!","");
                Long num = Long.parseLong(rep);
                for (int i = 1; i <= num; i++) {
                    fact1 = fact1 * i;
                }
                res.setText(Long.toString(fact1));
                String arth=res.getText().toString();
                dbHelper.insert("SCIENTIFIC", input + " = " + arth);
            } else if (input.contains("sin")) {

                //String rep=input.replaceAll("sin","");
                input = input.replaceAll("[^0-9]", "");
                double sine = Math.sin(Double.parseDouble(input));
                res.setText(Double.toString(sine));
                String arth=res.getText().toString();
                dbHelper.insert("SCIENTIFIC", input + " = " + arth);
            } else if (input.contains("cos")) {

                //String rep=input.replaceAll("sin","");
                input = input.replaceAll("[^0-9]", "");
                double cose = Math.cos(Double.parseDouble(input));
                res.setText(Double.toString(cose));
                String arth=res.getText().toString();
                dbHelper.insert("SCIENTIFIC", input + " = " + arth);
            } else if (input.contains("tan")) {

                //String rep=input.replaceAll("sin","");
                input = input.replaceAll("[^0-9]", "");
                double tane = Math.tan(Double.parseDouble(input));

                res.setText(Double.toString(tane));
                String arth=res.getText().toString();
                dbHelper.insert("SCIENTIFIC", input + " = " + arth);
            } else if (input.contains("cosec")) {

                //String rep=input.replaceAll("sin","");
                input = input.replaceAll("[^0-9]", "");
                //Double inp=(1/ Math.sin(Double.parseDouble(input)));
                double cosece = 1 / Math.sin(Double.parseDouble(input));
                res.setText(Double.toString(cosece));
                String arth=res.getText().toString();
                dbHelper.insert("SCIENTIFIC", input + " = " + arth);
            } else if (input.contains("c") || input.contains("C")) {
                input = input.replaceAll("[^cC0-9]", "");
                String[] perm = input.split("c|C");
                for (int i = 0; i < perm.length; i++) {
                    // Toast.makeText(MainActivity.this, perm[i], Toast.LENGTH_LONG).show();
                    if (Integer.parseInt(perm[1]) > Integer.parseInt(perm[0])) {
                        TastyToast.makeText(getApplicationContext(), " Please enter proper text", TastyToast.LENGTH_LONG,
                                TastyToast.INFO);
                    } else {
                        int sec = Integer.parseInt(perm[0]) - Integer.parseInt(perm[1]);
                        int num = Integer.parseInt(perm[0]);
                        int num1 = Integer.parseInt(perm[1]);
                        for (i = 1; i <= num; i++) {
                            fact1 = fact1 * i;
                        }
                        for (i = 1; i <= num1; i++) {
                            r = r * i;
                        }
                        for (i = 1; i <= sec; i++) {
                            fact2 = fact2 * i;
                        }
                        int com = fact2 * r;

                        int result = fact1 / com;
                        res.setText(Integer.toString(result));
                        String arth=res.getText().toString();
                        dbHelper.insert("SCIENTIFIC", input + " = " + arth);
                    }
                }
            } else {
                TastyToast.makeText(getApplicationContext(), " Please enter valid text", TastyToast.LENGTH_LONG,
                        TastyToast.INFO);
            }
        }
        catch (Exception e){
            TastyToast.makeText(getApplicationContext(), "Doesn't Scan properly ! Try again later ", TastyToast.LENGTH_LONG,
                    TastyToast.ERROR);
        }
    }

}
