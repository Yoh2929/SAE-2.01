module SAE201 {
	requires javafx.controls;
	requires javafx.base;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.front.controllers to javafx.graphics, javafx.fxml;
}
