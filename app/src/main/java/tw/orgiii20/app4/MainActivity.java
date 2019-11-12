package tw.orgiii20.app4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private EditText editText1;
    private TextView textView1;
    private String answer = "";
    private int counter = 0;
    private int dig = 4;//位數
    private boolean isWIN = false;
    private long lastTime = 0;
    private int gameMod=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answer = GameStart(dig);
        Log.i("onCreate", "onCreate");
    }

    private String GameStart(int dig){
        LinkedList<Integer> list = new LinkedList<>();
        for(int i=0; i < 10; i++){
            list.add(i);
        }
        Collections.shuffle(list);

        StringBuffer sb =new StringBuffer();
        for(int i=0; i < dig; i++){
            sb.append(list.get(i));
        }

        Log.i("GameStart", "sb: "+sb.toString());
        return sb.toString();
    }//產生題目

    private String checkAB(String strInput){
        int a=0,b=0;
        for(int i=0; i < strInput.length(); i++){
            if(strInput.charAt(i) == answer.charAt(i)){
                a++;
            }else if(answer.indexOf(strInput.charAt(i)) != -1){
                b++;
            }
        }
        return a+"A"+b+"B";
    }

    public void input1(View view) {
        editText1 = findViewById(R.id.ans1);
        textView1 = findViewById(R.id.ans2);
        String strInput = editText1.getText().toString();
        if (!isRight(strInput)){
            return;
        }

        counter++;
        String result = checkAB(strInput);
        textView1.append("counter: "+counter+strInput+" --> "+result+"\n");

        if(result.equals(dig+"A0B")){//WIN
            isWIN=true;
            showLog();
        }else if(counter == 10){//LOST
            showLog();
        }
        Log.i("input1", "strInput: " + strInput + "counter: " + counter);
    }

    private void showLog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(isWIN? "WIN":"LOST")
                .setMessage("isWIN?")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NewGame(null);
                    }
                })
                .create();
        alertDialog.show();
        textView1 = findViewById(R.id.ans2);
        textView1.append("遊戲結束\n");
    }

    private boolean isRight(String strInput){

        if(strInput.matches("^[0-9]{"+dig+"}$")){
            return true;
        }
        else{
            return false;
        }
    }

    public void NewGame(View view) {
        counter = 0;
        isWIN = false;
        textView1 = findViewById(R.id.ans2);
        textView1.setText("");
        answer = GameStart(dig);
        textView1.append("新遊戲\n");
        Log.i("NewGame", "NewGame");
    }

    public void GameSet(View view) {
        String[] digSelect={"1","2","3","4","5","6"};
        AlertDialog alertDialog =new AlertDialog.Builder(this)
                .setTitle("digSelect")
                .setSingleChoiceItems(digSelect, dig-1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("GameSet","GameSet: "+which);
                        int gameMod = which;
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dig = gameMod;
                        NewGame(null);
                    }
                })
                .setNegativeButton("Cancel",null)
                .setCancelable(false)
                .create();
        alertDialog.show();
    }

    public void ExitGame(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No",null)
                .create();
        alertDialog.show();
    }

    public void editClick(View view) {
        editText1 = findViewById(R.id.ans1);
        editText1.setText("");
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis()-lastTime > 3*1000){
            Log.i("onBackPressed", "lastTime: "+lastTime+" currentTime: "+System.currentTimeMillis());
            lastTime = System.currentTimeMillis();
            Toast.makeText(this,"Back Pressed Again",Toast.LENGTH_SHORT).show();
        }else{
            super.onBackPressed();
        }
        Log.i("onBackPressed","onBackPressed");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("onDestroy","onDestroy");
    }

}
