package android.fattyduck.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    // 0 = yellow 1 = red
    int activePlayer = 0;

    //initialize gamestate array to make sure players can't mark spots that are already marked
    int[] gameState={2,2,2,2,2,2,2,2,2};


    //using multidimension array to hold winning positions
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8}, {0,5,8}, {2,4,6} };

    boolean isGameActive =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view){

        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter]==2 && isGameActive) {
            gameState[tappedCounter] = activePlayer;

            //creating animation hiding at above 1000 then dropping down with 2 rotations at .4seconds
            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotation(720).setDuration(400);
        }

        TextView winnerTextView = (TextView) findViewById(R.id.WinnerTextView);

        for(int winningPosition[] : winningPositions){
            //checking for a win
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]]
                    && gameState[winningPosition[1]]  == gameState[winningPosition[2]]
                    && gameState[winningPosition[1]] != 2){
                isGameActive = false;

                if(gameState[winningPosition[0]]==0){
                    winnerTextView.setText("Yellow Wins");
                }else{
                    winnerTextView.setText("Red Wins");
                }
                LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                layout.setVisibility(View.VISIBLE);
            }else {
                //checking if its a draw
                boolean isFull = true;
                for(int counterState : gameState){
                    if(counterState == 2) isFull = false;
                }
                if(isFull){
                    winnerTextView.setText("It is a Draw");
                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                }
            }
        }


    }

    public void playAgain(View view){

        //resetting game so humans can play again
        activePlayer = 0;

        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);

        layout.setVisibility(View.INVISIBLE);

        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }


        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i = 0; i<gridLayout.getChildCount(); i++){

            //getting ImageView from GridView childs and setting transparent background
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageResource(android.R.drawable.screen_background_light_transparent);

        }

        isGameActive = true;
    }

}
