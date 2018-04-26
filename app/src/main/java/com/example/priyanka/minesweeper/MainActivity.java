package com.example.priyanka.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Collections;
import java.util.Random;
import android.*;
import android.media.MediaPlayer;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    public GridView gv;
    public String a;
    public ArrayList<String> items = new ArrayList<String>();
    String[][] minegrid = new String[9][9];
    int mines =0;
    int f = 0,p=0,w=0,flag=0,win=0;

    private Handler timer = new Handler();
    private int secondsPassed = 0;

    public void startTimer()
    {
        if (secondsPassed == 0)
        {
            timer.removeCallbacks(updateTimeElasped);
            timer.postDelayed(updateTimeElasped, 1000);
        }
    }

    public void stopTimer()
    {
       
        timer.removeCallbacks(updateTimeElasped);
    }

   
    private Runnable updateTimeElasped = new Runnable()
    {
        public void run()
        {
            long currentMilliseconds = System.currentTimeMillis();
            ++secondsPassed;
            TextView txttimer =(TextView) findViewById(R.id.txttimer);
           txttimer.setText("Time : "+ Integer.toString(secondsPassed) +" seconds");

            
            timer.postAtTime(this, currentMilliseconds);
            timer.postDelayed(updateTimeElasped, 1000);
        }
    };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MediaPlayer click= MediaPlayer.create(MainActivity.this,R.raw.s1);
        final MediaPlayer bomb= MediaPlayer.create(MainActivity.this,R.raw.bomb);



        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                minegrid[i][j] = " ";
            }
        }



        /*for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                items.add(minegrid[i][j]);
            }
        }*/
        for(int i=0;i<81;i++) {
            items.add("O");
        }

        //final TextView result =(TextView) findViewById(R.id.result);
         Button easy = (Button) findViewById(R.id.easy);
         Button medium = (Button) findViewById(R.id.medium);
         Button adv = (Button) findViewById(R.id.adv);
         Button playagain = (Button) findViewById(R.id.playagain);
        //TextView timer =(TextView) findViewById(R.id.timer);
        final TextView msg =(TextView) findViewById(R.id.msg);
        //timer.setText("Timer");
        TextView txttimer =(TextView) findViewById(R.id.txttimer);
        txttimer.setText("Time : ");

        gv = (GridView) this.findViewById(R.id.MyGrid);
        final CustomGridAdaptor gridadapter = new CustomGridAdaptor(MainActivity.this, items);
        gv.setEnabled(false);
        if (gv.isEnabled()==false) {
            //Toast.makeText(getApplicationContext(), "Please choose the difficulty Level", Toast.LENGTH_LONG).show();
            msg.setText("Please select the dificulty Level");
            //msg.setTextColor();
        }




        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f=0;
                mines = 8;
                gv.setEnabled(true);
                msg.setText("");


            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f=0;
                mines = 24;
                gv.setEnabled(true);
                msg.setText("");
            }
        });

        adv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f=0;
                mines = 40;
                gv.setEnabled(true);
                msg.setText("");
            }
        });

        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                f=0;
                mines=0;
                stopTimer();
                msg.setText("Please select the dificulty Level");

                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        minegrid[i][j] = " ";
                    }
                }


                for(int i=0;i<81;i++) {
                    items.set(i,"O");
                }
                gv.setAdapter(gridadapter);
            }
        });



        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

               click.start();




                int x = position/9;
                int y = position%9;
               // int i = checkWin()

                if(minegrid[x][y]=="*"){

                    //result.setText("Game Over");
                    bomb.start();
                    mines=0;

                    items.set(position, minegrid[position / 9][position % 9]);
                    for(int i=0;i<9;i++) {
                        for (int j = 0; j < 9; j++) {
                            if(minegrid[i][j]=="*")
                            items.set((9*i+j),minegrid[i][j]);
                        }
                    }
                    for(int i=0;i<81;i++){
                        if(items.get(i)=="F" && minegrid[i/9][i%9]!="*")
                        {
                            items.set(i,"X");
                        }

                        if(items.get(i)=="F" && minegrid[i/9][i%9]=="*")
                        {
                            items.set(i,"*");
                        }
                    }
                    gv.setEnabled(false);

                    stopTimer();
                    secondsPassed = 0;
                    gv.setAdapter(gridadapter);
                    Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_LONG).show();

                }

                else {


                    if (f == 0) {
                        createMines(position);

                        //if(mines!=0) {
                            //items.set(position, minegrid[position / 9][position % 9]);
                        click(x,y);
                        startTimer();
                        gv.setAdapter(gridadapter);
                        f++;
                       // }

                        //click(x,y);


                    } else {


                        //items.set(position, minegrid[position / 9][position % 9]);

                        click(x, y);
                        int win = checkWin();
                        if(win==1){

                            gv.setEnabled(false);
                            stopTimer();
                            Toast.makeText(getApplicationContext(), "Congratulations!You Won", Toast.LENGTH_LONG).show();
                            gv.setAdapter(gridadapter);

                        }

                        else
                        gv.setAdapter(gridadapter);

                    }


                }


                    gv.setAdapter(gridadapter);


            }
        });

        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                items.set(position, "F");
                gv.setAdapter(gridadapter);


                return true;
            }
        });


                gv.setAdapter(gridadapter);








    }

    public void createMines(int position) {

        if (mines == 0) {
            Toast.makeText(getApplicationContext(), "Please choose the difficulty Level", Toast.LENGTH_LONG).show();

        } else {

            Random rand = new Random();

            int count = 0;
            //int x=position/9;
            //int y=position%9;
            int xPoint;
            int yPoint;
            //int noOfMines = 10;
            while (count < mines) {

                xPoint = rand.nextInt(9);
                yPoint = rand.nextInt(9);
                p = (9 * xPoint) + yPoint;
                if (minegrid[xPoint][yPoint] != "*" && p != position ) {

                    minegrid[xPoint][yPoint] = "*";
                    count++;

                }
            }

            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    if (minegrid[x][y] != "*") {
                        minegrid[x][y] = neighbors(x, y);
                    }
                }
            }
        }
    }

    public String neighbors(int y, int x) {
        int c = 0;
        c += minePresent(y - 1, x - 1);
        c += minePresent(y - 1, x);
        c += minePresent(y - 1, x + 1);
        c += minePresent(y, x - 1);
        c += minePresent(y, x + 1);
        c += minePresent(y + 1, x - 1);
        c += minePresent(y + 1, x);
        c += minePresent(y + 1, x + 1);
        if (c > 0)
            return Integer.toString(c);
         else
            return " ";

    }

    public int minePresent(int x, int y) {

        if (x >= 0 && x < 9 && y >= 0 && y < 9 && minegrid[x][y] == "*")
            return 1;

        else
            return 0;

    }


    public void click( int x , int y ){
        if( x >= 0 && y >= 0 && x < 9 && y < 9 && items.get((9*x)+y)=="O"&&minegrid[x][y]!="*"){

            items.set((9*x)+y,minegrid[x][y]);

            if( neighbors(x,y)==" " ){
                for( int xt = -1 ; xt <= 1 ; xt++ ){
                    for( int yt = -1 ; yt <= 1 ; yt++){

                            click(x + xt , y + yt);

                    }
                }
            }


        }


    }

    int checkWin(){
        int c=0;
        w=0;
        flag=0;

        int O=0;
        for(int i=0;i<81;i++){

            for(int j=1;j<mines-1;j++){
                if(minegrid[i/9][i%9]!= Integer.toString(j) && minegrid[i/9][i%9]!=" ");
                c=1;

            }

            if(items.get(i)=="O"&&c==1)
                O++;
            if(items.get(i)=="F" && minegrid[i/9][i%9]=="*")
                flag++;
        }

        if(O+flag == mines)
            w=1;
        return w;
    }
}

