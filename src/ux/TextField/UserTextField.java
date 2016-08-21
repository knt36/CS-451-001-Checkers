package ux.TextField;

public class UserTextField extends TextField {
    public UserTextField(String hint) {
        super(hint);
    }

    public boolean isValidPassUser() {
        //Checks for null and length
        String user = this.getText();
        String pass = this.getText();
        return !(!this.userInputedText || !this.userInputedText || user.length() < 3 || pass.length() < 3 || user == null || pass == null);
    }
}
