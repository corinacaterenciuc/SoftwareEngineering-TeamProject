package theotherhalf.superconference.validators;

import org.apache.commons.validator.EmailValidator;
import org.springframework.stereotype.Component;
import theotherhalf.superconference.domain.User;
import theotherhalf.superconference.exceptions.ValidationException;


@Component
public class UserValidator implements BasicValidator<User>
{
    public UserValidator()
    {
    }

    private boolean validateEmail(String email)
    {
        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(email);
    }

    @Override
    public void validate(User entity) throws ValidationException
    {
        if(null == entity)
        {
            throw new ValidationException("[VALIDATION ERROR] Null user provided");
        }
        if(null == entity.getEmail())
        {
            throw new ValidationException("[VALIDATION ERROR] Null user e-mail provided");
        }
        else if(!validateEmail(entity.getEmail()))
        {
            throw new ValidationException("[VALIDATION ERROR] E-mail address is not a valid e-mail address");
        }
        if(null == entity.getFirst_name() || null == entity.getLast_name())
        {
            throw new ValidationException("[VALIDATIOR ERROR] Null user names provided");
        }
        else if(entity.getFirst_name().strip().equals("") || entity.getLast_name().strip().equals(""))
        {
            throw new ValidationException("[VALIDATION ERROR] User names should not be blank");
        }
    }
}
