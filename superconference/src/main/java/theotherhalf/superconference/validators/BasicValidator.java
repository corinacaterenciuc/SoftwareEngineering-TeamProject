package theotherhalf.superconference.validators;

import theotherhalf.superconference.exceptions.ValidationException;

public interface BasicValidator<T>
{
    void validate(T entity) throws ValidationException;
}
