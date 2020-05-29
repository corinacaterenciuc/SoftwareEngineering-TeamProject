package theotherhalf.superconference.exceptions;

public class CMSForbidden extends ConferenceManagementSystemException
{
    public CMSForbidden(String message)
    {
        super(message);
    }

    public CMSForbidden(String message, Throwable cause)
    {
        super(message, cause);
    }

    public CMSForbidden(Throwable cause)
    {
        super(cause);
    }
}