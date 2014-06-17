package uk.ac.ox.it.skossuggester.jerseyutils;

import io.dropwizard.jersey.params.AbstractParam;
import javax.validation.ValidationException;


public class NotEmptyStringParam extends AbstractParam<String> {
    public NotEmptyStringParam(String input) {
        super(input);
    }

    @Override
    protected String errorMessage(String input, Exception e) {
        return "Not a valid string";
    }

    @Override
    protected String parse(String input) throws ValidationException {
        if (input == null || input.isEmpty()) {
            throw new ValidationException("String cannot be empty");
        } 
       return input;
    }
}
