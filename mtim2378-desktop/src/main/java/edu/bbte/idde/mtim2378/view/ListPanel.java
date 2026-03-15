package edu.bbte.idde.mtim2378.view;

import edu.bbte.idde.mtim2378.service.TodoService;
import edu.bbte.idde.mtim2378.service.TodoServiceFactory;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

public class ListPanel extends JPanel {
    private final JTextArea textArea;
    private final JButton getButton;

    private final TodoService todoService = TodoServiceFactory.getInstance();

    public ListPanel() {
        super();

        textArea = new JTextArea();
        textArea.setEditable(false);

        getButton = new JButton("Get All Todos");

        getButton.addActionListener(e -> {
            textArea.setText("");
            todoService.getAllTodos().forEach(t -> textArea.append(t.getId() + " " + t.getTitle() + " "
                    + t.getDeadLine() + " " + t.getSeverity() + "\n"));
        });

        setLayout(new BorderLayout());
        add(textArea, BorderLayout.CENTER);
        add(getButton, BorderLayout.SOUTH);
    }
}
