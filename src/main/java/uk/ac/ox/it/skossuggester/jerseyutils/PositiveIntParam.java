package uk.ac.ox.it.skossuggester.jerseyutils;

import io.dropwizard.jersey.params.AbstractParam;
import javax.validation.ValidationException;

/**
 *
 * @author martinfilliau
 */
public class PositiveIntParam extends AbstractParam<Integer> {
    public PositiveIntParam(String input) {
        super(input);
    }

    @Override
    protected Integer parse(String input) {
        int parsed = Integer.valueOf(input);
        if (parsed < 1) {
            throw new ValidationException("int must be positive");
        }
        return parsed;
    }
}
