package view;

import application.Hasher;
import application.Main;
import facade.UserFacadeBeanRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.PasswordField;

/**
 * Class, that controlls Register window, which is responsible for new user creation.
 * @author Ivan Gulis
 */
public class RegisterController {
	@FXML
	private TextField textFieldUsernameR;
	@FXML
	private TextField textFieldNickR;
	@FXML
	private PasswordField passwordFieldR;
	@FXML
	private PasswordField passwordFieldR2;
	@FXML
	private Label labelUsernameR;
	@FXML
	private Label labelPasswordR;
	@FXML
	private Label labelPasswordR2;
	@FXML
	private Label labelMainCaption;
	@FXML
	private Label labelPasswordError;
	@FXML
	private Label labelNickR;
	@FXML
	private Button buttonCreateAccount;
	@FXML
	private Menu menuLanguage;
	@FXML
	private ComboBox<String> comboBoxServer;
	
	private static Logger LOG = Logger.getLogger(RegisterController.class.getName());
	
	public Label getLabelUsernameR() {
		return labelUsernameR;
	}

	public Label getLabelPasswordR() {
		return labelPasswordR;
	}

	public Label getLabelPasswordR2() {
		return labelPasswordR2;
	}

	public Label getLabelMainCaption() {
		return labelMainCaption;
	}

	public Label getLabelPasswordError() {
		return labelPasswordError;
	}

	public Label getLabelNickR() {
		return labelNickR;
	}

	public Button getButtonCreateAccount() {
		return buttonCreateAccount;
	}

	public Menu getMenuLanguage() {
		return menuLanguage;
	}

	public ComboBox<String> getComboBoxServer() {
		return comboBoxServer;
	}

	public void setComboBoxServer(ComboBox<String> comboBoxServer) {
		this.comboBoxServer = comboBoxServer;
	}

	/** Function, that creates new account for user */
	// Event Listener on Button[#buttonCreateAccount].onAction
	@FXML
	public void createAccount(ActionEvent event) {
		Context ctx = null;
		labelPasswordError.setText("");
		String username = textFieldUsernameR.getText();
		String password = passwordFieldR.getText();
		if (!"".equals(password)) {
			String text = Main.getInstance().getLanguage().getString("EmptyLogin");
			labelPasswordError.setText(text);
			return;
		} 
		String server = comboBoxServer.getSelectionModel().getSelectedItem();
		String nick = textFieldNickR.getText();
		String confirm = passwordFieldR2.getText();
		try {
			ctx = new InitialContext();
			UserFacadeBeanRemote remote = (UserFacadeBeanRemote) ctx.lookup("ProjectEAR"
					+ "/ProjectServer//UserFacadeBean!facade.UserFacadeBeanRemote");
			LOG.info("Connection to UserFacadeBean initialized");
			if (confirm.equals(password)){
				Hasher hasher = new Hasher();
				String salt = hasher.getSalt();
				String hashedPassword = hasher.getHash(password, salt);
				remote.createAccount(username, hashedPassword, salt.toString(), server, nick);
				String text = Main.getInstance().getLanguage().getString("AccountCreated");
				labelPasswordError.setText(text);
				LOG.info("User was successfully created in database.");
			} else {
				String text = Main.getInstance().getLanguage().getString("DifferentPasswords");
				labelPasswordError.setText(text);
				LOG.warning("Given passwords do not match.");
			}
		} catch (NamingException e) { //logging from this function
			LOG.log(Level.SEVERE, "Context was not able to be initialized - name resolution has failed.", e);
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) { //logging from hasher
			LOG.log(Level.SEVERE, "Error: No hash was generated - MessageDigestSpi implementation for the specified algorithm"
					+ " (MD5) is not available.", e);
			String text = Main.getInstance().getLanguage().getString("HashingFailed");
			labelPasswordError.setText(text);
		} catch (RuntimeException e) { //logging from createAccount
			String text = Main.getInstance().getLanguage().getString("AlreadyRegistered");
			labelPasswordError.setText(text);
			LOG.log(Level.SEVERE, "Error: User " + username + " already exists - Failed to create user in database.", e);
		} finally {
			try {
				ctx.close();
			} catch (NamingException e) {
				LOG.log(Level.SEVERE, "Context was not able to be closed - name resolution has failed.", e);
			}
		}
	}
	
	/** Function, that changes language to slovak */
	// Event Listener on MenuItem[#menuItemSlovak].onAction
	@FXML
	public void changeSlovak(ActionEvent event) {
		Main.getInstance().getLanguage().setLanguage("sk");
		Main.getInstance().renameAll();
	}
	
	/** Function, that changes language to english */
	// Event Listener on MenuItem[#menuItemEnglish].onAction
	@FXML
	public void changeEnglish(ActionEvent event) {
		Main.getInstance().getLanguage().setLanguage("eng");
		Main.getInstance().renameAll();
	}
}
