package fr.epita.android.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class PlayGameActivity extends AppCompatActivity {

    private ImageView[][] gridImages;
    private GridLayout grid;
    private TextView textPlayer1, textPlayer2, textPlayer1Score, textPlayer2Score, textWinPlayer;
    private MaterialButton btn_restart;

    private boolean isGameEnded = false;
    private int currentPlayer = 1;
    private int winPlayer = 0;
    private int player1WinCount, player2WinCount;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        gridImages = new ImageView[3][3];
        grid = findViewById(R.id.layout_grid);
        textPlayer1 = findViewById(R.id.text_player_1);
        textPlayer2 = findViewById(R.id.text_player_2);
        textPlayer1Score = findViewById(R.id.text_player_1_score);
        textPlayer2Score = findViewById(R.id.text_player_2_score);
        textWinPlayer = findViewById(R.id.text_player_win);
        btn_restart = findViewById(R.id.btn_restart);

        player1WinCount = Integer.parseInt(textPlayer1Score.getText().toString());
        player2WinCount = Integer.parseInt(textPlayer2Score.getText().toString());

        if (currentPlayer == 1) {
            textPlayer1.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPlayerTurn, null));
            textPlayer1Score.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPlayerTurn, null));
            textPlayer2.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorTextPlayer, null));
            textPlayer2Score.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorTextPlayer, null));
        } else {
            textPlayer1.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorTextPlayer, null));
            textPlayer1Score.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorTextPlayer, null));
            textPlayer2.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPlayerTurn, null));
            textPlayer2Score.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPlayerTurn, null));
        }
        loadGrid();

        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayer = 1;
                textPlayer1.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPlayerTurn, null));
                textPlayer1Score.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPlayerTurn, null));
                textPlayer2.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorTextPlayer, null));
                textPlayer2Score.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorTextPlayer, null));
                textWinPlayer.setText("");
                for (int i = 0; i < gridImages.length; i++) {
                    for (int j = 0; j < gridImages[i].length; j++) {
                        gridImages[i][j].setBackgroundResource(0);
                    }
                }
                loadGrid();
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void loadGrid() {
        btn_restart.setVisibility(View.INVISIBLE);
        for (int i = 0; i < gridImages.length; i++) {
            for (int j = 0; j < gridImages[i].length; j++) {
                gridImages[i][j] = new ImageView(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(i);
                params.columnSpec = GridLayout.spec(j);
                params.width = 350;
                params.height = 350;
                params.topMargin = 5;
                params.bottomMargin = 5;
                params.leftMargin = 5;
                params.rightMargin = 5;
                gridImages[i][j].setLayoutParams(params);
                gridImages[i][j].setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
                grid.addView(gridImages[i][j]);
                Log.d("DEBUG", String.valueOf(gridImages[i][j].getDrawable()));
                gridImages[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getBackground() != null && !v.getBackground().getConstantState().equals(getDrawable(R.drawable.ic_x).getConstantState()) && !v.getBackground().getConstantState().equals(getDrawable(R.drawable.ic_o).getConstantState())) {
                            if (currentPlayer == 1) {
                                v.setBackground(getDrawable(R.drawable.ic_x));
                                changePlayer();
                            } else {
                                v.setBackground(getDrawable(R.drawable.ic_o));
                                changePlayer();
                            }
                        }
                    }
                });
            }
        }
    }

    private void changePlayer() {
        if (isGameEnded()) {
            textPlayer1Score.setText(String.valueOf(player1WinCount));
            textPlayer2Score.setText(String.valueOf(player2WinCount));
            textWinPlayer.setText("Player " + winPlayer + " won!");
            btn_restart.setVisibility(View.VISIBLE);
        } else {
            currentPlayer = (currentPlayer == 1 ? 2 : 1);
            if (currentPlayer == 1) {
                textPlayer1.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPlayerTurn, null));
                textPlayer1Score.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPlayerTurn, null));
                textPlayer2.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorTextPlayer, null));
                textPlayer2Score.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorTextPlayer, null));
            } else {
                textPlayer1.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorTextPlayer, null));
                textPlayer1Score.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorTextPlayer, null));
                textPlayer2.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPlayerTurn, null));
                textPlayer2Score.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPlayerTurn, null));
            }
        }
    }

    private boolean isGameEnded() {
        for (int i = 0; i < gridImages.length; i++) {
            if (gridImages[i][0].getBackground() != null && gridImages[i][1].getBackground() != null && gridImages[i][2].getBackground() != null) {
                if (gridImages[i][0].getBackground().getConstantState().equals(gridImages[i][1].getBackground().getConstantState()) &&
                        gridImages[i][1].getBackground().getConstantState().equals(gridImages[i][2].getBackground().getConstantState())) {
                    if (gridImages[i][0].getBackground().getConstantState().equals(getDrawable(R.drawable.ic_x).getConstantState())) {
                        winPlayer = 1;
                        player1WinCount++;
                    } else if (gridImages[i][0].getBackground().getConstantState().equals(getDrawable(R.drawable.ic_o).getConstantState())) {
                        winPlayer = 2;
                        player2WinCount++;
                    }
                    isGameEnded = true;
                    return isGameEnded;
                }
            }

            if (gridImages[0][i].getBackground() != null && gridImages[1][i].getBackground() != null && gridImages[2][i].getBackground() != null) {
                if (gridImages[0][i].getBackground().getConstantState().equals(gridImages[1][i].getBackground().getConstantState()) &&
                        gridImages[1][i].getBackground().getConstantState().equals(gridImages[2][i].getBackground().getConstantState())) {
                    if (gridImages[0][i].getBackground().getConstantState().equals(getDrawable(R.drawable.ic_x).getConstantState())) {
                        winPlayer = 1;
                        player1WinCount++;
                    } else if (gridImages[0][i].getBackground().getConstantState().equals(getDrawable(R.drawable.ic_o).getConstantState())) {
                        winPlayer = 2;
                        player2WinCount++;
                    }
                    isGameEnded = true;
                    return isGameEnded;
                }
            }
        }

        if (gridImages[0][0].getBackground() != null && gridImages[1][1].getBackground() != null && gridImages[2][2].getBackground() != null) {
            if (gridImages[0][0].getBackground().getConstantState().equals(gridImages[1][1].getBackground().getConstantState()) &&
                    gridImages[1][1].getBackground().getConstantState().equals(gridImages[2][2].getBackground().getConstantState())) {
                if (gridImages[0][0].getBackground().getConstantState().equals(getDrawable(R.drawable.ic_x).getConstantState())) {
                    winPlayer = 1;
                    player1WinCount++;
                } else if (gridImages[0][0].getBackground().getConstantState().equals(getDrawable(R.drawable.ic_o).getConstantState())) {
                    winPlayer = 2;
                    player2WinCount++;
                }
                isGameEnded = true;
                return isGameEnded;
            }
        }

        if (gridImages[2][0].getBackground() != null && gridImages[1][1].getBackground() != null && gridImages[0][2].getBackground() != null) {
            if (gridImages[2][0].getBackground().getConstantState().equals(gridImages[1][1].getBackground().getConstantState()) &&
                    gridImages[1][1].getBackground().getConstantState().equals(gridImages[0][2].getBackground().getConstantState())) {
                if (gridImages[2][0].getBackground().getConstantState().equals(getDrawable(R.drawable.ic_x).getConstantState())) {
                    winPlayer = 1;
                    player1WinCount++;
                } else if (gridImages[2][0].getBackground().getConstantState().equals(getDrawable(R.drawable.ic_o).getConstantState())) {
                    winPlayer = 2;
                    player2WinCount++;
                }
                isGameEnded = true;
                return isGameEnded;
            }
        }

        for (int i = 0; i < gridImages.length; i++) {
            for (int j = 0; j < gridImages[i].length; j++) {
                if (gridImages[i][j].getBackground() != null && !gridImages[i][j].getBackground().getConstantState().equals(getDrawable(R.drawable.ic_x).getConstantState()) && !gridImages[i][j].getBackground().getConstantState().equals(getDrawable(R.drawable.ic_o).getConstantState())) {
                    isGameEnded = false;
                    return isGameEnded;
                }
            }
        }

        isGameEnded = true;
        return isGameEnded;
    }
}
