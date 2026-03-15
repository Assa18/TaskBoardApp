package edu.bbte.idde.mtim2378.view;

import edu.bbte.idde.mtim2378.model.Severity;
import edu.bbte.idde.mtim2378.model.Todo;
import edu.bbte.idde.mtim2378.service.ServiceException;
import edu.bbte.idde.mtim2378.service.TodoServiceFactory;
import edu.bbte.idde.mtim2378.service.TodoService;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddPanel extends JPanel {
    private final JTextField titleInput;
    private final JTextField descriptionInput;
    private final JTextField dateInput;
    private final JComboBox<Severity> severityInput;
    private final JButton submitButton;

    private final TodoService todoService = TodoServiceFactory.getInstance();

    public AddPanel() {
        super();

        titleInput = new JTextField("Write title here");
        descriptionInput = new JTextField("Write description here");
        dateInput = new JTextField("Write date here: yyyy-MM-dd HH:mm");
        severityInput = new JComboBox<>();
        severityInput.addItem(Severity.LAZY);
        severityInput.addItem(Severity.NORMAL);
        severityInput.addItem(Severity.URGENT);

        submitButton = new JButton("Submit");

        add(submitButton);
        add(titleInput);
        add(descriptionInput);
        add(dateInput);
        add(severityInput);

        submitButton.addActionListener(e -> {
            try {
                LocalDateTime deadLine = LocalDateTime.parse(dateInput.getText(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                Todo todo = new Todo(titleInput.getText(), descriptionInput.getText(), deadLine,
                        (Severity) severityInput.getSelectedItem());

                todoService.addTodo(todo);
            } catch (DateTimeParseException | ServiceException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
