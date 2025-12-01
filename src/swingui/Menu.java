package swingui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Controller;

/**
 * Menu panel containing game controls and player information.
 * Provides buttons for starting new games, saving and loading game states,
 * and displays wall counts for each player.
 * 
 * @author banhalmitibor
 * @version 1.0
 */
public class Menu extends JPanel {
    
    /**
     * Constructs a new Menu panel with all control elements.
     * Includes wall count displays, new game buttons, and save/load controls.
     */
    public Menu(){
        this.setBounds(726, 50, 400, 620);
        this.setBackground(new Color(245, 245, 245)); 
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Wall count displays for each player
        JPanel walldisplays = new JPanel();
        walldisplays.setOpaque(false);
        walldisplays.setLayout(new BoxLayout(walldisplays, BoxLayout.Y_AXIS));
        walldisplays.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Walls remaining"
        ));

        Font wallFont = new Font("SansSerif", Font.PLAIN, 14);
        for (int i = 0; i < 4; i++) {
            JLabel l = new WallDisplay(i);
            l.setFont(wallFont);
            l.setAlignmentX(LEFT_ALIGNMENT);
            walldisplays.add(l);
            walldisplays.add(Box.createVerticalStrut(4));
        }

        this.add(walldisplays, BorderLayout.NORTH);

    
        JPanel middlePanel = new JPanel(new GridBagLayout());
        middlePanel.setOpaque(false);
        middlePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "New Game"
        ));

        JButton newBut2 = new JButton("New 2 player game");
        JButton newBut4 = new JButton("New 4 player game");
        JButton newButAi = new JButton("New AI game");

        Font buttonFont = new Font("SansSerif", Font.PLAIN, 13);
        Dimension buttonSize = new Dimension(150, 28);
        for (JButton b : new JButton[]{newBut2, newBut4, newButAi}) {
            b.setFont(buttonFont);
            b.setFocusPainted(false);
            b.setPreferredSize(buttonSize);
        }

        newBut2.addActionListener(ae -> {
            Controller.reset(2);
        });
        newBut4.addActionListener(ae -> {
            Controller.reset(4);
        });
        newButAi.addActionListener(ae -> {
            Controller.resetAi();
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.gridx = 0;
        gbc.gridy = 0;
        middlePanel.add(newBut2, gbc);
        gbc.gridy = 1;
        middlePanel.add(newBut4, gbc);
        gbc.gridy = 2;
        middlePanel.add(newButAi, gbc);

        this.add(middlePanel, BorderLayout.CENTER);

    
        // Save/Load controls
        JPanel savePanel = new JPanel();
        savePanel.setOpaque(false);
        savePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Save / Load"
        ));

        JComboBox<Integer> saveChooser = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        saveChooser.setPreferredSize(new Dimension(60, 24));

        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        for (JButton b : new JButton[]{saveButton, loadButton}) {
            b.setFont(buttonFont);
            b.setFocusPainted(false);
        }

        saveButton.addActionListener(evt -> {
            try {
                Controller.saveGame((int) saveChooser.getSelectedItem());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        loadButton.addActionListener(evt -> {
            try {
                Controller.loadGame((int) saveChooser.getSelectedItem());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        savePanel.add(new JLabel("Slot:"));
        savePanel.add(Box.createHorizontalStrut(5));
        savePanel.add(saveChooser);
        savePanel.add(Box.createHorizontalStrut(10));
        savePanel.add(saveButton);
        savePanel.add(Box.createHorizontalStrut(5));
        savePanel.add(loadButton);

        this.add(savePanel, BorderLayout.SOUTH);
    }
}