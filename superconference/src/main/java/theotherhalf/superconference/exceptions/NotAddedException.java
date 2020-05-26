package theotherhalf.superconference.exceptions;

public class NotAddedException extends ConferenceManagementSystemException
{
    public NotAddedException(String message)
    {
        super(message);
    }

    public NotAddedException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NotAddedException(Throwable cause)
    {
        super(cause);
    }
}
