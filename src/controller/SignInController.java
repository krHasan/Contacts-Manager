package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import controller.dialog.ErrorDialogController;
import controller.dialog.InputDialogController;
import controller.dialog.PasswordDialogController;
import controller.dialog.WarningDialogController;
import database.CreateDatabase;
import database.access.Credentials;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.SignInModal;
import operation.GetDialog;
import operation.GetScence;
import system.DeleteUserCredentials;

public class SignInController {

	/////////////////////////////////// ObjectsDeclaration////////////////////////////////
	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Button btnSignIn;

	@FXML
	private Label lblForgotPassword;

	@FXML
	private Label lblNewUser;

	@FXML
	private Label lblWarning;

	/////////////////////////////////// GlobalVariables////////////////////////////////
	SignInModal model;
	GetScence getWindow = new GetScence();
	GetDialog getDialog = new GetDialog();

	/////////////////////////////////// GeneralCode////////////////////////////////
	@FXML
	private void initialize() {
		CreateDatabase.createDatabase();
		
		model = new SignInModal();

		if (!model.isDBConnected()) {
			lblWarning.setText("Database not found");
			lblNewUser.setDisable(true);
			allNodesDown();
		} else if (!Credentials.isUserPresent()) {
			allNodesDown();
		}

	}

	private Map<String, Object> thisStageInfo() {
		Map<String, Object> map = new HashMap<>();
		Stage stage = (Stage) btnSignIn.getScene().getWindow();
		Scene scene = (Scene) btnSignIn.getScene();
		double height = scene.getHeight(), width = scene.getWidth();
		map.put("stage", stage);
		map.put("height", height);
		map.put("width", width);

		return map;
	}

	private void allNodesDown() {
		txtUsername.setDisable(true);
		txtPassword.setDisable(true);
		btnSignIn.setDisable(true);
		lblForgotPassword.setDisable(true);
	}

	//////////////////////////////////////////// MainCode////////////////////////////////////////////
	// --------------------------------------------------------------------------------------------//

	@FXML
	private void btnSignIn(ActionEvent event) throws IOException {
		if (model.signIn(txtUsername.getText(), txtPassword.getText())) {
			getWindow.dashboard(thisStageInfo());
		} else {
			lblWarning.setText("Username or Password is Wrong");
			txtPassword.clear();
		}
	}

	@FXML
	private void warningLblStates() {
		lblWarning.setText(null);
	}

	@FXML
	private void lblForgotPassword(MouseEvent e) {
		// calling the input dialog
		InputDialogController.headerText = "Answer The Question";
		InputDialogController.contentText = model.securityQuestion();

		// show and wait
		getDialog.inputDialog(thisStageInfo());
		((Scene) btnSignIn.getScene()).getRoot().setEffect(null);

		if (InputDialogController.btnOKpressed) {
			if (model.isSecurityAnswerIsRight(InputDialogController.answer)) {
				getWindow.forgotPassword(thisStageInfo());
			} else {
				ErrorDialogController.headerText = "Wrong Answer";
				ErrorDialogController.contentText = "The answer you have given is wrong";
				// show and wait
				getDialog.errorDialog(thisStageInfo());
				((Scene) btnSignIn.getScene()).getRoot().setEffect(null);
			}
		}
	}

	@FXML
	private void lblNewUser(MouseEvent e) {
		if (Credentials.isUserPresent()) {

			WarningDialogController.headerText = "User Exists";
			WarningDialogController.contentText = "You have to delete this user to create a new one.";
			// show and wait
			getDialog.warningDialog(thisStageInfo());
			((Scene) btnSignIn.getScene()).getRoot().setEffect(null);

			// user wants to delete existing user's data
			if (WarningDialogController.btnOKpressed) {

				InputDialogController.headerText = "Confirm Username";
				InputDialogController.contentText = "Type existing user's username";
				// show and wait
				getDialog.inputDialog(thisStageInfo());
				((Scene) btnSignIn.getScene()).getRoot().setEffect(null);

				// given username and pressed OK
				if (InputDialogController.btnOKpressed) {

					// username is right
					if (model.isUsernameIsRight(InputDialogController.answer)) {

						// show and wait
						getDialog.passwordDialog(thisStageInfo());
						((Scene) btnSignIn.getScene()).getRoot().setEffect(null);

						// given password and pressed OK
						if (PasswordDialogController.btnOKpressed) {

							// password is right
							if (model.isPasswordIsRight(PasswordDialogController.answer)) {

								InputDialogController.headerText = "Answer The Question";
								InputDialogController.contentText = model.securityQuestion();

								// show and wait
								getDialog.inputDialog(thisStageInfo());
								((Scene) btnSignIn.getScene()).getRoot().setEffect(null);

								// given answer and pressed OK
								if (InputDialogController.btnOKpressed) {

									// answer is right
									if (model.isSecurityAnswerIsRight(InputDialogController.answer)) {

										// call delete class and delete user data
										new DeleteUserCredentials().deleteAllData();
										getWindow.registration(thisStageInfo());

									} else { // answer is wrong
										ErrorDialogController.headerText = "Wrong Answer";
										ErrorDialogController.contentText = "The answer you have given is wrong";
										// show and wait
										getDialog.errorDialog(thisStageInfo());
										((Scene) btnSignIn.getScene()).getRoot().setEffect(null);
									}
								}

							} else { // password is wrong
								ErrorDialogController.headerText = "Wrong Password";
								ErrorDialogController.contentText = "The password you have given is wrong";
								// show and wait
								getDialog.errorDialog(thisStageInfo());
								((Scene) btnSignIn.getScene()).getRoot().setEffect(null);
							}
						}

					} else { // username is wrong
						ErrorDialogController.headerText = "Wrong Username";
						ErrorDialogController.contentText = "The username you have given is wrong";
						// show and wait
						getDialog.errorDialog(thisStageInfo());
						((Scene) btnSignIn.getScene()).getRoot().setEffect(null);
					}
				}

			}
		} else {
			getWindow.registration(thisStageInfo());
		}
	}

}
