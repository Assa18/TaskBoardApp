package edu.bbte.idde.mtim2378.view;

import edu.bbte.idde.mtim2378.service.ServiceException;
import edu.bbte.idde.mtim2378.service.TodoService;
import edu.bbte.idde.mtim2378.service.TodoServiceFactory;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;

public class DeletePanel extends JPanel {
    private final JTextField textField;
    private final JButton deleteButton;

    private final TodoService todoService =  TodoServiceFactory.getInstance();

    public DeletePanel() {
        super();

        deleteButton = new JButton("Delete");
        textField = new JTextField("Write todo id");

        deleteButton.addActionListener(e -> {
            try {
                Long id = Long.parseLong(textField.getText());
                todoService.deleteTodo(id);
            } catch (ServiceException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setLayout(new BorderLayout());
        add(textField, BorderLayout.WEST);
        add(deleteButton, BorderLayout.EAST);
    }
}
