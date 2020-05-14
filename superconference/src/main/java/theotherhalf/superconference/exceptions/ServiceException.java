package theotherhalf.superconference.exceptions;

public class ServiceException extends ConferenceManagementSystemException
{
    public ServiceException(String message)
    {
        super(message);
    }

    public ServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ServiceException(Throwable cause)
    {
        super(cause);
    }
}