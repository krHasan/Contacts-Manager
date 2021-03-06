package model;

import database.access.Credentials;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import operation.ComboboxList;

public class RegistrationModal extends Credentials {
	
	public ObservableList<String> getSecurityQuestionList() {
		ObservableList<String> list = FXCollections.observableArrayList(new ComboboxList().getSecurityQuestionList());
		return list;
	}
	
}
