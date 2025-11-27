module OOADLab {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
	requires java.sql;
    
    opens main to javafx.graphics, javafx.fxml;
}
