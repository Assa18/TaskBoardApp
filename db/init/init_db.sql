USE idde;

CREATE TABLE Todos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    description VARCHAR(256) NOT NULL,
    deadline DATETIME NOT NULL,
    severity VARCHAR(50) NOT NULL,
    done BIT
);
