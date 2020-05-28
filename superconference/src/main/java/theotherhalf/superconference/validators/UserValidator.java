package theotherhalf.superconference.validators;

import org.apache.commons.validator.EmailValidator;
import org.springframework.stereotype.Component;
import theotherhalf.superconference.domain.CMSUser;
import theotherhalf.superconference.exceptions.ValidationException;


@Component
public class UserValidator implements BasicValidator<CMSUser>
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
    public void validate(CMSUser entity) throws ValidationException
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
        if(null == entity.getFirstName() || null == entity.getLastName())
        {
            throw new ValidationException("[VALIDATIOR ERROR] Null user names provided");
        }
        else if(entity.getFirstName().strip().equals("") || entity.getLastName().strip().equals(""))
        {
            throw new ValidationException("[VALIDATION ERROR] CMSUser names should not be blank");
        }
    }
}
