package edu.bbte.idde.mtim2378.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class TodoView extends JFrame {
    private final AddPanel addPanel;
    private final DeletePanel deletePanel;
    private final ListPanel listPanel;
    private final UpdatePanel updatePanel;

    private final JPanel contentPanel;

    public TodoView() {
        super();
        addPanel = new AddPanel();
        deletePanel = new DeletePanel();
        listPanel = new ListPanel();
        updatePanel = new UpdatePanel();

        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2, 2));
        contentPanel.add(addPanel);
        contentPanel.add(deletePanel);
        contentPanel.add(listPanel);
        contentPanel.add(updatePanel);

        setContentPane(contentPanel);
        setBounds(20, 20, 500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
