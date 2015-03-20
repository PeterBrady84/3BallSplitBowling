package model;

/**
 * Created by Peter on 19/03/2015.
 */
public class NumberValidator {

    public boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            // is not numeric
            return false;
        }
    }
}
