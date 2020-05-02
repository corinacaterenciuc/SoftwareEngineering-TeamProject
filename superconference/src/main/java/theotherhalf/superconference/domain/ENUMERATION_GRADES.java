package theotherhalf.superconference.domain;

public enum ENUMERATION_GRADES
{
    STRONG_REJECT (-3),
    REJECT (-2),
    WEAK_REJECT (-1),
    BORDERLINE (0),
    WEAK_ACCEPT (1),
    ACCEPT (2),
    STRONG_ACCEPT (3);

    private final Integer grade;

    ENUMERATION_GRADES(final Integer grade)
    {
        this.grade = grade;
    }
}
