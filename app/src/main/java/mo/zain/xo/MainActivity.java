package mo.zain.xo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean player1turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private AlertDialog dialog;
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPlayer1 = findViewById(R.id.textView);
        textViewPlayer2 = findViewById(R.id.textView2);
        textView=findViewById(R.id.textView4);
        imageView=findViewById(R.id.imageView3);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!((Button) v).getText().toString().equals("")) {
                            return;
                        }
                        if (player1turn) {
                            ((Button) v).setText("X");
                            ((Button) v).setTextColor(R.color.teal_200);
                            textView.setText("Currant Player :2");
                        } else {
                            ((Button) v).setText("O");
                            ((Button) v).setTextColor(R.color.black);
                            textView.setText("Currant Player :1");
                        }
                        roundCount++;
                        if (checkForWin()) {
                            if (player1turn) {
                                player1Wins();
                            } else {
                                player2Wins();
                            }
                        } else if (roundCount == 9) {
                            draw();
                        } else {
                            player1turn = !player1turn;
                        }
                    }

                });
            }
        }
    }

    private void showUrlDialog(String txt)
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        View view= LayoutInflater.from(this).inflate(
                R.layout.layout_dialog,
                (ViewGroup)findViewById(R.id.layoutAddUrlContainer)
        );

        builder.setView(view);
        dialog=builder.create();
        if (dialog.getWindow()!=null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        TextView textView=view.findViewById(R.id.textView3);
        textView.setText(txt);
        view.findViewById(R.id.textYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePointsText();
                resetBoard();
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.textNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    private void player1Wins() {
        player1Points++;
        showUrlDialog("Player 1 win ! Do you Want To Play again?");
    }

    private void player2Wins() {
        player2Points++;
        showUrlDialog("Player 2 win ! Do you Want To Play again?");
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Player 1:" + player1Points);
        textViewPlayer2.setText("Player 2:" + player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        textView.setText("Currant Player :1");
        roundCount = 0;
        player1turn = true;

    }

    private void draw() {
        showUrlDialog("No one win ! Do you Want To Play again?");
        //resetBoard();
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2]) &&
                    !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i]) &&
                    !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2]) &&
                !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0]) &&
                !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

}

