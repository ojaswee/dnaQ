package dnaQ.GUI;

import dnaQ.Connections.DatabaseConnections;
import dnaQ.GUI.tables.CommentTable;
import dnaQ.Models.Mutation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommentFrame extends JFrame {

    JTextArea txtComment;
    JButton btnSave;

    Mutation currentMutation;
    CommentTable parent;

    private Integer frameWidth;
    private Integer frameHeight;

    public CommentFrame (CommentTable parent, Mutation mutation){

        super ("Comment Section");

        this.parent=parent;
        this.currentMutation = mutation;

        frameWidth = (GUICommonTools.screenWidth)/5;
        frameHeight = (GUICommonTools.screenHeight)/3;

        createComponents();
        layoutComponents();

        pack();
        setAlwaysOnTop(true);
        setResizable(true);
        setLocationRelativeTo(parent);



        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleSaveButtonClick();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        });
    }

    private void createComponents() {

        txtComment = new JTextArea();
        txtComment.setText(currentMutation.comment);
        txtComment.setEnabled(true);

        btnSave = new JButton("Save");

    }

    private void layoutComponents() {

        setMinimumSize(new Dimension(frameWidth, frameHeight));

        JPanel txtPanel = new JPanel();
        txtPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JScrollPane txtScroll = new JScrollPane(txtComment);
        txtPanel.add(txtScroll);

        add(txtComment,BorderLayout.CENTER);
        add(btnSave,BorderLayout.PAGE_END);
    }

    private void handleSaveButtonClick() throws Exception {

        currentMutation.setComment(txtComment.getText());

        //update database
        DatabaseConnections.updateComment(currentMutation.comment,currentMutation.usertestid.toString(),
                currentMutation.chr, currentMutation.pos.toString(),currentMutation.ref,currentMutation.alt);

        CommentFrame.this.dispose();

    }

}
