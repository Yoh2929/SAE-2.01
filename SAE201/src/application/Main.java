package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import application.front.FenPrincipale;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Main extends Application{
	
	public void start(Stage stage){
		stage = new FenPrincipale();
		stage.show();
	}
	
	public static void main(String[] args) {
		Application.launch();
	}
	
	public static boolean isDateValid(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
	
	public static boolean isNumeric(String s) {
		boolean res = true;
	    if (s == null) {
	        res = false;
	    }
	    try {
	        Double.parseDouble(s);
	    } catch (NumberFormatException e) {
	        res = false;
	    }
	    return res;
	}
	
	public static boolean isNumericInt(String s) {
		boolean res = true;
	    if (s == null) {
	        res = false;
	    }
	    try {
	        Integer.parseInt(s);
	    } catch (NumberFormatException e) {
	        res = false;
	    }
	    return res;
	}
	
	public static boolean contains(Integer[] arr, int val) {
		boolean res = false;
		for (int i : arr) {
			if (i == val) {
				res = true;
			}
		}
		return res;
	}
	
	public static void replaceStringTextField(TextField t, String d, String a) {
		int index_float;
		if (t.getText().contains(d)) {
			index_float = t.getText().indexOf(d);
			t.setText(t.getText().replace(d, a));
			t.positionCaret(index_float + 1);
		}
	}
	
	public static ArrayList<Node> getAllNodes(Parent root) {
	    ArrayList<Node> nodes = new ArrayList<Node>();
	    addAllDescendents(root, nodes);
	    return nodes;
	}

	private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
	    for (Node node : parent.getChildrenUnmodifiable()) {
	        nodes.add(node);
	        if (node instanceof Parent)
	            addAllDescendents((Parent)node, nodes);
	    }
	}
}
