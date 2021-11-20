package io.github.tobero.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.tobero.nameGen.NameGenerator;
import io.github.tobero.soccerPlayer.SoccerPlayer;
import io.github.tobero.soccerPlayer.SoccerPlayerRegistry;
import io.github.tobero.soccerPlayer.Stat;
import io.github.tobero.utils.JsonUtility;
import io.github.tobero.utils.ObjectPair;
import io.github.tobero.utils.Utils;
import org.checkerframework.checker.units.qual.A;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import static javax.swing.JOptionPane.showMessageDialog;


import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

public class EditorWindow {

    private JFrame frame;
    private JTextField firstNameText;
    private JTextField lastNameText;
    private JTable table;
    private JComboBox<String> nameGenerationMethodeSelectionBox;
    private JButton saveButton;
    private JComboBox<SoccerPlayer.Positions> PositionComboBox;
    private JTree playerSelection;
    private JButton generateName;
    private JButton addButton;
    private JTextField priceTextField;


    private String path;

    private DefaultMutableTreeNode selectedNode;
    private Map<String, SoccerPlayer> loadedPlayer = new HashMap<>();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                EditorWindow window = new EditorWindow();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public EditorWindow() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(0, 0, 1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        nameGenerationMethodeSelectionBox = new JComboBox<String>(new String[] {
                "Sampled", "Procedural"
        });

        JLabel labelPlayerNameGeneration = new JLabel("Player name generation");

        firstNameText = new JTextField();
        firstNameText.setColumns(10);
        firstNameText.addActionListener(e -> {
            loadedPlayer.get(selectedNode.toString()).setFirstName(firstNameText.getText());
        });

        JLabel firstNameLabel = new JLabel("First Name:");

        JLabel lastNameLabel = new JLabel("Last Name:");

        lastNameText = new JTextField();
        lastNameText.setColumns(10);
        lastNameText.addActionListener(e -> {
            loadedPlayer.get(selectedNode.toString()).setSecondName(lastNameText.getText());
        });

        JScrollPane playerStatsTable = new JScrollPane();

        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            if (path == null) {
                showMessageDialog(null, "You need to load a file first");
                return;
            }
            SavePlayerStats(loadedPlayer.get(selectedNode.toString()));

            SoccerPlayerRegistry registry = new SoccerPlayerRegistry();
            Map<String, SoccerPlayer> map = new HashMap<>();

            for (SoccerPlayer soccerPlayer : loadedPlayer.values()) {
                map.put(soccerPlayer.getFirstName() +":"+ soccerPlayer.getSecondName(), soccerPlayer);
            }
            registry.setSoccerPlayerMap(map);
            registry.setVersion("BETAv1.0.0");
            JsonUtility.SaveObject(new File(path), registry);
        });

        PositionComboBox = new JComboBox<>(SoccerPlayer.Positions.values());
        PositionComboBox.addActionListener(e -> {
            loadedPlayer.get(selectedNode.toString()).setPosition((SoccerPlayer.Positions) PositionComboBox.getSelectedItem());
        });

        JLabel positionLabel = new JLabel("Position");

        playerSelection = new JTree();

        generateName = new JButton("Generate name");
        generateName.addActionListener(e -> {
            if (nameGenerationMethodeSelectionBox.getSelectedItem().equals("Sampled")) {
                List<String> names = NameGenerator.getSampledNames(1);
                String[] split = names.get(0).split(" ");
                firstNameText.setText(split[0]);
                lastNameText.setText(split[1]);

                loadedPlayer.get(selectedNode.toString()).setSecondName(lastNameText.getText());
                loadedPlayer.get(selectedNode.toString()).setFirstName(firstNameText.getText());
            } else {
                List<String> names = NameGenerator.getProcedualNames(1, 3, 10, 5000);
                String[] split = names.get(0).split(" ");
                firstNameText.setText(split[0]);
                lastNameText.setText(split[1]);

                loadedPlayer.get(selectedNode.toString()).setSecondName(lastNameText.getText());
                loadedPlayer.get(selectedNode.toString()).setFirstName(firstNameText.getText());
            }
        });

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> FileChooser.choosePath(s -> {
            LoadFile(s);
        }));

        playerSelection.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
            System.out.println(loadedPlayer.keySet());
            if (loadedPlayer.containsKey(node.toString())) {
                if (selectedNode != null) {
                    SavePlayerStats(loadedPlayer.get(selectedNode.toString()));
                }
                SoccerPlayer player = loadedPlayer.get(node.toString());
                selectedNode = node;
                OpenPlayer(player);
            }
        });

        addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            SoccerPlayer player = GeneratePlayer();
            loadedPlayer.put(player.toString(), player);
            ReloadPlayerTree();
        });


        priceTextField = new JTextField();
        priceTextField.setText("1");
        priceTextField.setColumns(10);
        priceTextField.addActionListener(e -> {
            ObjectPair<Boolean, Integer> priceObj = Utils.getInt(priceTextField.getText());
            if (priceObj.getFirst()) {
                loadedPlayer.get(selectedNode.toString()).setPrice(priceObj.getSecond());
            } else {
                showMessageDialog(null, "The price is no whole number");
            }
        });

        JLabel priceLabel = new JLabel("Price");

        JButton reloadButton = new JButton("Reload");
        reloadButton.addActionListener(e -> {
            ReloadPlayerTree();
        });

        JLabel average = new JLabel("Average");

        JLabel averageLabel = new JLabel("Average:");

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            if (selectedNode != null) {
                loadedPlayer.remove(selectedNode.toString());
            }
            OpenPlayer(null);
            ReloadPlayerTree();
        });

        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(playerSelection, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                .addGroup(groupLayout.createSequentialGroup()
                                                        .addGap(18)
                                                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                .addGroup(groupLayout.createSequentialGroup()
                                                                        .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                                                                .addGroup(groupLayout.createSequentialGroup()
                                                                                        .addComponent(reloadButton)
                                                                                        .addGap(121))
                                                                                .addGroup(groupLayout.createSequentialGroup()
                                                                                        .addComponent(averageLabel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                                                                        .addGap(18)
                                                                                        .addComponent(average, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                                                                        .addGap(18)))
                                                                        .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                                                                .addGroup(groupLayout.createSequentialGroup()
                                                                                        .addComponent(loadButton)
                                                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                                                        .addComponent(saveButton)
                                                                                        .addContainerGap())
                                                                                .addGroup(groupLayout.createSequentialGroup()
                                                                                        .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                                                                                .addGroup(groupLayout.createSequentialGroup()
                                                                                                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                                                                                                .addGroup(groupLayout.createSequentialGroup()
                                                                                                                        .addComponent(labelPlayerNameGeneration)
                                                                                                                        .addGap(30)
                                                                                                                        .addComponent(firstNameLabel))
                                                                                                                .addGroup(groupLayout.createSequentialGroup()
                                                                                                                        .addComponent(nameGenerationMethodeSelectionBox, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                                                                                                        .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                                                                        .addComponent(firstNameText)))
                                                                                                        .addGap(18)
                                                                                                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                                                                                                .addComponent(lastNameText)
                                                                                                                .addComponent(lastNameLabel))
                                                                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                                                                        .addComponent(generateName)
                                                                                                        .addGap(135)
                                                                                                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                                                                .addComponent(PositionComboBox, Alignment.TRAILING, 0, 268, Short.MAX_VALUE)
                                                                                                                .addComponent(positionLabel)))
                                                                                                .addComponent(playerStatsTable, GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE))
                                                                                        .addGap(18))))
                                                                .addGroup(groupLayout.createSequentialGroup()
                                                                        .addComponent(addButton)
                                                                        .addGap(18)
                                                                        .addComponent(removeButton)
                                                                        .addGap(867))))
                                                .addGroup(groupLayout.createSequentialGroup()
                                                        .addGap(216)
                                                        .addComponent(priceTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addContainerGap()))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(240)
                                                .addComponent(priceLabel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap())))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(playerSelection, GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                                                .addGroup(groupLayout.createSequentialGroup()
                                                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                                .addComponent(addButton)
                                                                .addComponent(removeButton))
                                                        .addGap(11)
                                                        .addComponent(reloadButton)))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(42)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                                        .addComponent(labelPlayerNameGeneration)
                                                                        .addComponent(firstNameLabel)
                                                                        .addComponent(lastNameLabel))
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                                        .addComponent(nameGenerationMethodeSelectionBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(firstNameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lastNameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(generateName)))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addComponent(positionLabel)
                                                                .addGap(18)
                                                                .addComponent(PositionComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(playerStatsTable, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(average)
                                                        .addComponent(averageLabel))
                                                .addGap(29)
                                                .addComponent(priceLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(priceTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED, 433, Short.MAX_VALUE)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(saveButton)
                                                        .addComponent(loadButton))))
                                .addContainerGap(12, Short.MAX_VALUE))
        );

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(new DefaultTableModel(
                new Integer[][] {
                        {0, 0, 0, 0, 0, 0, 0},
                },
                new String[] {
                        "Pace", "Shooting", "Passing", "Dribbling", "Defending", "Goal Fending", "Physical"
                }
        ));
        playerStatsTable.setViewportView(table);
        ReloadPlayerTree();
        frame.getContentPane().setLayout(groupLayout);
    }

    private void OpenPlayer(SoccerPlayer player) {
        if (player == null) {
            firstNameText.setText("[Removed]");
            lastNameText.setText("[Removed]");
            PositionComboBox.setSelectedItem(SoccerPlayer.Positions.STRIKER);
            priceTextField.setText("[Removed]");

            table.setValueAt(-1, 0, 0);
            table.setValueAt(-1, 0, 1);
            table.setValueAt(-1, 0, 2);
            table.setValueAt(-1, 0, 3);
            table.setValueAt(-1, 0, 4);
            table.setValueAt(-1, 0, 5);
            table.setValueAt(-1, 0, 6);
        } else {
            firstNameText.setText(player.getFirstName());
            lastNameText.setText(player.getSecondName());
            PositionComboBox.setSelectedItem(player.getPosition());
            priceTextField.setText(""+player.getPrice());

            table.setValueAt(player.getPace().getValue(), 0, 0);
            table.setValueAt(player.getShooting().getValue(), 0, 1);
            table.setValueAt(player.getPassing().getValue(), 0, 2);
            table.setValueAt(player.getDribbling().getValue(), 0, 3);
            table.setValueAt(player.getDefending().getValue(), 0, 4);
            table.setValueAt(player.getGoalFending().getValue(), 0, 5);
            table.setValueAt(player.getPhysical().getValue(), 0, 6);
        }
    }

    private void LoadFile(String path) {
        this.path = path;
        loadedPlayer.clear();
        SoccerPlayerRegistry registry = null;
        try {
            registry = JsonUtility.ReadObject(new File(path), SoccerPlayerRegistry.class);
        } catch (Exception e) {
        }
        if (registry != null) {
            for (SoccerPlayer player : registry.getSoccerPlayerMap().values()) {
                loadedPlayer.put(player.getFirstName() + " " + player.getSecondName(), player);
            }
        } else {
            showMessageDialog(null, "You opened a invaild file. Nothing got loaded, but everything is still working");
        }

        ReloadPlayerTree();
    }

    private SoccerPlayer GeneratePlayer() {
        String[] names = getSampledName();
        return new SoccerPlayer(names[0], names[1], SoccerPlayer.Positions.STRIKER, new Stat("Pace"),
                new Stat("Shooting"), new Stat("Passing"), new Stat("Dribbling"),
                new Stat("Defending"), new Stat("Goal Fending"), new Stat("Physical"));
    }

    private String[] getSampledName() {
        List<String> names = NameGenerator.getSampledNames(1);
        return names.get(0).split(" ");
    }

    private String[] getProcduralName() {
        List<String> names = NameGenerator.getProcedualNames(1, 3, 10, 5000);
        return names.get(0).split(" ");
    }

    private void SavePlayerStats(SoccerPlayer player) {
        if (player != null) {
            player.getPace().setValue(GetValue(0));
            player.getShooting().setValue(GetValue(1));
            player.getPassing().setValue(GetValue(2));
            player.getDribbling().setValue(GetValue(3));
            player.getDefending().setValue(GetValue(4));
            player.getGoalFending().setValue(GetValue(5));
            player.getPhysical().setValue(GetValue(6));
        }
    }

    private float GetValue(int column) {
        if (table.getValueAt(0, column) instanceof String) {
            return Utils.getFloat((String) table.getValueAt(0, column));
        } else {
            return (float) table.getValueAt(0, column);
        }
    }

    private void ReloadPlayerTree() {
        Map<SoccerPlayer.Positions, DefaultMutableTreeNode> map = new HashMap<>();

        DefaultTreeModel model = (DefaultTreeModel) playerSelection.getModel();
        // Clearing
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.removeAllChildren();
        model.reload();

        model = (DefaultTreeModel) playerSelection.getModel();
        root = (DefaultMutableTreeNode) model.getRoot();

        for (SoccerPlayer.Positions position : SoccerPlayer.Positions.values()) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(position.toString());
            model.insertNodeInto(node, root, root.getChildCount());
            map.put(position, node);
            playerSelection.scrollPathToVisible(new TreePath(node.getPath()));
        }


        for (SoccerPlayer player : loadedPlayer.values()) {
            model = (DefaultTreeModel) playerSelection.getModel();

            MutableTreeNode node = map.get(player.getPosition());
            System.out.println(node);
            MutableTreeNode newNode = new DefaultMutableTreeNode(player.toString());

            model.insertNodeInto(newNode, node, node.getChildCount());
        }
    }
}

