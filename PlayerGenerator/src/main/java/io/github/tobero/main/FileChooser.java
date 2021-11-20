package io.github.tobero.main;

import io.github.tobero.utils.TCallback;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {

    private static final String waitTo = "waitTo";

    private JFrame frame;
    private JTextField textField;

    public static void choosePath(TCallback<String> pathCallback) {
        FileChooser chooser = new FileChooser(pathCallback);
    }

    public FileChooser(TCallback<String> pathCallback) {
        initialize(pathCallback);
    }


    private void initialize(TCallback<String> pathCallback) {
        frame = new JFrame();
        frame.setType(Window.Type.UTILITY);
        frame.setBounds(100, 100, 358, 536);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        textField = new JTextField();
        textField.setColumns(10);

        JLabel selectFileLabel = new JLabel("Select the file:");

        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser;
                if (!textField.getText().isEmpty()) {
                    chooser = new JFileChooser(textField.getText());
                } else {
                    chooser = new JFileChooser();
                }

                FileNameExtensionFilter filter = new FileNameExtensionFilter("SoccerPlayer File", "json");
                chooser.setFileFilter(filter);

                int choice = chooser.showOpenDialog(null);

                if (choice == JFileChooser.APPROVE_OPTION) {
                    textField.setText(chooser.getSelectedFile().getPath());
                }
            }
        });

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pathCallback.Callback(textField.getText());
                frame.dispose();
            }
        });

        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(browseButton))
                                        .addComponent(selectFileLabel))
                                .addContainerGap(24, Short.MAX_VALUE))
                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                .addContainerGap(243, Short.MAX_VALUE)
                                .addComponent(okButton)
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(27)
                                .addComponent(selectFileLabel)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(browseButton))
                                .addPreferredGap(ComponentPlacement.RELATED, 388, Short.MAX_VALUE)
                                .addComponent(okButton)
                                .addContainerGap())
        );
        frame.getContentPane().setLayout(groupLayout);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}

