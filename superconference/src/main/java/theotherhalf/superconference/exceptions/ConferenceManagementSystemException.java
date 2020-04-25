package theotherhalf.superconference.exceptions;

public class ConferenceManagementSystemException extends RuntimeException
{
    public ConferenceManagementSystemException(String message)
    {
        super(message);
    }

    public ConferenceManagementSystemException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ConferenceManagementSystemException(Throwable cause)
    {
        super(cause);
    }
}
