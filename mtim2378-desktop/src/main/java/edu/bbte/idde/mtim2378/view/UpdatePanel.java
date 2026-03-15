package edu.bbte.idde.mtim2378.view;

import edu.bbte.idde.mtim2378.model.Severity;
import edu.bbte.idde.mtim2378.model.Todo;
import edu.bbte.idde.mtim2378.service.ServiceException;
import edu.bbte.idde.mtim2378.service.TodoService;
import edu.bbte.idde.mtim2378.service.TodoServiceFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UpdatePanel extends JPanel {
    private final JButton getButton;
    private final JTextField getField;

    private final JTextField titleField;
    private final JTextField descriptionField;
    private final JTextField dateField;
    private final JComboBox<Severity> severityInput;
    private final JButton submitButton;

    private final TodoService todoService = TodoServiceFactory.getInstance();

    public UpdatePanel() {
        super();
        getButton = new JButton("Get");
        getField = new JTextField("Enter id");

        titleField = new JTextField();
        descriptionField = new JTextField();
        dateField = new JTextField();
        severityInput = new JComboBox<>();
        severityInput.addItem(Severity.LAZY);
        severityInput.addItem(Severity.NORMAL);
        severityInput.addItem(Severity.URGENT);
        submitButton = new JButton("Submit");

        setLayout(new GridLayout(4, 2));
        add(getField);
        add(getButton);
        add(titleField);
        add(dateField);
        add(descriptionField);
        add(severityInput);
        add(submitButton);

        getButton.addActionListener(e -> {
            Todo todo;
            try {
                Long id = Long.parseLong(getField.getText());
                todo = todoService.getById(id);

                titleField.setText(todo.getTitle());
                descriptionField.setText(todo.getDescription());
                dateField.setText(todo.getDeadLine().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                severityInput.setSelectedItem(todo.getSeverity());
            } catch (NumberFormatException | ServiceException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid id", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        submitButton.addActionListener(e -> {
            try {
                LocalDateTime deadLine = LocalDateTime.parse(dateField.getText(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                Todo todo = new Todo(titleField.getText(), descriptionField.getText(), deadLine,
                        (Severity) severityInput.getSelectedItem());
                todo.setId(Long.parseLong(getField.getText()));
                todoService.updateTodo(todo);
            } catch (DateTimeParseException | ServiceException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
