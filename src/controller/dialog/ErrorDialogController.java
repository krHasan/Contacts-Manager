package controller.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorDialogController {
	
	/////////////////////////////////// Objects////////////////////////////////
	@FXML
	private Label dialogHeader;

	@FXML
	private Label content;

	@FXML
	private Button btnOK;

	/////////////////////////////////// GlobalVariables////////////////////////////////
	public static boolean btnOKpressed = false;
	public static String headerText = "Error!!!!";
	public static String contentText = "Something is wrong";

	/////////////////////////////////// GeneralCodes////////////////////////////////
	@FXML
	private void initialize() {
		dialogHeader.setText(headerText);
		content.setText(contentText);
	}

	//////////////////////////////////////////// MainCodes////////////////////////////////////////////
	// ---------------------------------------------------------------------------------------------//
	@FXML
	private void btnOK(ActionEvent e) {
		btnOKpressed = true;
		Stage stage = (Stage) btnOK.getScene().getWindow();
		stage.close();
	}

}
