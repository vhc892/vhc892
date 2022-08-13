package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private static int sec = 0;
    private static Timer timer = new Timer();
    private static JLabel lblTime;
    private static JButton  btnStart;
    private static Board board;

    
    public static void main(String[] args) {
        board = new Board();
        board.setEndGameListener(new EndGameListener() {
            @Override
            public void end(String player, int st) {
                if(st == Board.ST_WIN){
                    JOptionPane.showMessageDialog(null, "Người chơi " + player + " thắng");
                    stopGame();
                }else if(st == Board.ST_DRAW){
                    JOptionPane.showMessageDialog(null, "Hòa");
                    stopGame();
                }
            }
        }
        );

        JPanel jPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(jPanel,BoxLayout.Y_AXIS);
        jPanel.setLayout(boxLayout);


        board.setPreferredSize(new Dimension(500, 500));

        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(flowLayout);

        btnStart = new JButton("Start");

        //Timer và TimerTask
        lblTime = new JLabel("0:0");
        bottomPanel.add(lblTime);
        bottomPanel.add(btnStart);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(btnStart.getText().equals("Start")){
                    startGame();
                }else{
                    stopGame();
                }
            }
        }
        );

        jPanel.add(board);
        jPanel.add(bottomPanel);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame jFrame = new JFrame("Game co ca ro 9 o");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(true);
        jFrame.add(jPanel);

        //in ra giữa màn hìnhhình
        int x =  ((int)dimension.getWidth() / 2  - (jFrame.getWidth() /2 ));
        int y = ((int) dimension.getHeight() / 2 - (jFrame.getHeight() /2));

        jFrame.setLocation(x, y);
        
        //co giãn vừa layout bên trong
        jFrame.pack();
        
        jFrame.setVisible(true);
    }

    private static void startGame(){

        //Hỏi ai đi trước
        int choice = JOptionPane.showConfirmDialog(null, "Người chơi O đi trước đúng không?", "Ai là người đi trước?", JOptionPane.YES_NO_OPTION);

        board.reset();
        String currentPlayer = (choice == 0) ? Cell.O_VALUE : Cell.X_VALUE;
        board.setCurrentPlayer(currentPlayer);

        //Đếm
        sec = 0;
        lblTime.setText("0:0");
        timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sec++;
                String value = sec / 60 + " : " + sec % 60;
                lblTime.setText(value);
            }
        }, 1000, 1000);

        btnStart.setText("Stop");
    }

    private static void stopGame(){
        btnStart.setText("Start");

        sec = 0;
        lblTime.setText("0:0");

        timer.cancel();
        timer = new Timer();

        board.reset();
    }
}
