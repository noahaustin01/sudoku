/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sudoku;

/**
 *
 * @author noah
 */
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class Sudoku {

    class Tile extends JButton {

        int r;
        int c;

        Tile(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Sudoku");
    JTextArea textLabel = new JTextArea();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    JButton numSelected = null;
    int errors = 0;

    String puzzle_number;

    String[] puzzle;

    String[] solution;

    public Sudoku(String puzzle_num, String[] puzzle, String[] solution) {

        this.puzzle = puzzle;
        this.solution = solution;

        puzzle_number = puzzle_num;
        // frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        textLabel.setEditable(false);
        textLabel.setEnabled(false);
        textLabel.setForeground(new Color(255, 255, 0));
        textLabel.setBackground(textPanel.getBackground());
        textLabel.setFont(new Font("Arial", Font.BOLD, 30));
        textLabel.setAlignmentX(JLabel.CENTER);
        textLabel.setText("Sudoku: " + puzzle_number + "\n  Errors: " + errors);

        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(9, 9));
        setupTiles();
        frame.add(boardPanel, BorderLayout.CENTER);

        buttonsPanel.setLayout(new GridLayout(1, 9));
        setupButtons();
        frame.add(buttonsPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    void setupTiles() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                Tile tile = new Tile(r, c);
                char tileChar = puzzle[r].charAt(c);
                if (tileChar != '-') {
                    tile.setFont(new Font("Arial", Font.BOLD, 20));
                    tile.setText(String.valueOf(tileChar));
                    tile.setBackground(Color.lightGray);
                } else {
                    tile.setFont(new Font("Arial", Font.PLAIN, 20));
                    tile.setBackground(Color.white);
                }
                if ((r == 2 && c == 2) || (r == 2 && c == 5) || (r == 5 && c == 2) || (r == 5 && c == 5)) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 5, 5, Color.black));
                } else if (r == 2 || r == 5) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 5, 1, Color.black));
                } else if (c == 2 || c == 5) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 5, Color.black));
                } else {
                    tile.setBorder(BorderFactory.createLineBorder(Color.black));
                }
                tile.setFocusable(false);
                boardPanel.add(tile);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Tile tile = (Tile) e.getSource();
                        int r = tile.r;
                        int c = tile.c;
                        if (numSelected != null) {
                            if (tile.getText() != "") {
                                return;
                            }
                            String numSelectedText = numSelected.getText();
                            String tileSolution = String.valueOf(solution[r].charAt(c));
                            if (tileSolution.equals(numSelectedText)) {
                                tile.setText(numSelectedText);
                                puzzle[r] = puzzle[r].substring(0, c) + numSelectedText + puzzle[r].substring(c + 1, 9);
                                int correctCount = 0;
                                for (int p = 0; p < 9; p++) {
                                    if (puzzle[p].equals(solution[p])) {
                                        correctCount++;
                                    }
                                    if (correctCount == 9) {
                                        JOptionPane.showMessageDialog(textPanel, "You WON!!!!!");
                                        DifficultyChooserGUI chooser = new DifficultyChooserGUI();
                                        frame.dispose();
                                        chooser.setVisible(true);
                                    }
                                }

                            } else {
                                errors += 1;
                                textLabel.setText("Sudoku: " + puzzle_number + "\n  Errors: " + errors);
                                for (int i = 0; i < 8; i++) {
//                                    System.out.println(puzzle[i]);
                                }
//                                System.out.println("");

                            }

                        }
                    }
                }
                );
            }
        }
    }

    void setupButtons() {
        for (int i = 1; i < 10; i++) {
            JButton button = new JButton();
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setText(String.valueOf(i));
            button.setFocusable(false);
            button.setBackground(Color.white);
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    if (numSelected != null) {
                        numSelected.setBackground(Color.white);
                    }
                    numSelected = button;
                    numSelected.setBackground(Color.lightGray);
                }
            });
        }
    }
}
