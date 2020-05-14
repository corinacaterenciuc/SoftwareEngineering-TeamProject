package theotherhalf.superconference.exceptions;

public class ControllerException extends ConferenceManagementSystemException
{
    public ControllerException(String message)
    {
        super(message);
    }

    public ControllerException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ControllerException(Throwable cause)
    {
        super(cause);
    }
}