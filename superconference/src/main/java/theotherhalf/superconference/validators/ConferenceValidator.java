package theotherhalf.superconference.validators;

import org.springframework.stereotype.Component;
import theotherhalf.superconference.domain.Conference;
import theotherhalf.superconference.exceptions.ValidationException;

import java.util.Date;

@Component
public class ConferenceValidator implements BasicValidator<Conference>
{
    @Override
    public void validate(Conference entity) throws ValidationException
    {
        if(null == entity)
        {
            throw new ValidationException("[ERROR] Null conference provided");
        }
        if(null == entity.getName() || entity.getName().strip().equals(""))
        {
            throw new ValidationException("[ERROR] Null conference name provided");
        }
        if(null == entity.getZeroDeadline())
        {
            throw new ValidationException("[ERROR] Null conference date provided.");
        }

        int comparativ = entity.getZeroDeadline().compareTo(new Date());

        if(null != entity.getAbstractDeadline())
        {
            if(0 > entity.getAbstractDeadline().compareTo(new Date()))
            {
                throw new ValidationException("[ERROR] Conference date cannot be set into the past");
            }
            if(0 > entity.getAbstractDeadline().compareTo(entity.getZeroDeadline()))
            {
                throw new ValidationException("[ERROR] Conference abstract deadline cannot be set before zero deadline");
            }
        }

        if(null != entity.getProposalDeadline())
        {
            if(0 > entity.getProposalDeadline().compareTo(new Date()))
            {
                throw new ValidationException("[ERROR] Conference date cannot be set into the past");
            }
            if(0 > entity.getProposalDeadline().compareTo(entity.getAbstractDeadline()))
            {
                throw new ValidationException("[ERROR] Conference proposal deadline cannot be set before abstract deadline");
            }
        }

        if(null != entity.getBiddingDeadline())
        {
            if(0 > entity.getBiddingDeadline().compareTo(new Date()))
            {
                throw new ValidationException("[ERROR] Conference date cannot be set into the past");
            }
            if(0 > entity.getBiddingDeadline().compareTo(entity.getProposalDeadline()))
            {
                throw new ValidationException("[ERROR] Conference bidding deadline cannot be set before proposal deadline");
            }
        }

        if(null != entity.getEvaluationDeadline())
        {
            if(0 > entity.getEvaluationDeadline().compareTo(new Date()))
            {
                throw new ValidationException("[ERROR] Conference date cannot be set into the past");
            }
            if(0 > entity.getEvaluationDeadline().compareTo(entity.getBiddingDeadline()))
            {
                throw new ValidationException("[ERROR] Conference evaluation deadline cannot be set before bidding deadline");
            }
        }

        if(null != entity.getPresentationDeadline())
        {
            if(0 > entity.getPresentationDeadline().compareTo(new Date()))
            {
                throw new ValidationException("[ERROR] Conference date cannot be set into the past");
            }
            if(0 > entity.getPresentationDeadline().compareTo(entity.getEvaluationDeadline()))
            {
                throw new ValidationException("[ERROR] Conference presentation deadline cannot be set before evaluation deadline");
            }
        }

        // if ZeroDeadline has passed
        if (0 >= comparativ)
        {
            if(null == entity.getDescription() || entity.getDescription().strip().equals(""))
            {
                throw new ValidationException("[ERROR] Null conference description provided");
            }
            if(null == entity.getAbstractDeadline())
            {
                throw new ValidationException("[ERROR] Null conference date provided.");
            }

            // if abstractDeadline is before current date
            if(0 > entity.getAbstractDeadline().compareTo(new Date()))
            {
                throw new ValidationException("[ERROR] Conference date cannot be set into the past");
            }


            if(null == entity.getProposalDeadline())
            {
                throw new ValidationException("[ERROR] Null conference date provided.");
            }
            // if proposalDeadline is before current date
            if(0 > entity.getProposalDeadline().compareTo(new Date()))
            {
                throw new ValidationException("[ERROR] Conference date cannot be set into the past");
            }
            // if proposalDeadline is before abstractDeadline
            if(0 > entity.getProposalDeadline().compareTo(entity.getAbstractDeadline()))
            {
                throw new ValidationException("[ERROR] Conference proposal deadline cannot be set before abstract deadline");
            }


            if(null == entity.getBiddingDeadline())
            {
                throw new ValidationException("[ERROR] Null conference date provided.");
            }
            // if biddingDeadline is before current date
            if(0 > entity.getBiddingDeadline().compareTo(new Date()))
            {
                throw new ValidationException("[ERROR] Conference date cannot be set into the past");
            }
            // if biddingDeadline is before proposalDeadline
            if(0 > entity.getBiddingDeadline().compareTo(entity.getProposalDeadline()))
            {
                throw new ValidationException("[ERROR] Conference bidding deadline cannot be set before proposal deadline");
            }


            if (null == entity.getEvaluationDeadline())
            {
                throw new ValidationException("[ERROR] Null conference date provided.");
            }
            // if evaluationDeadline is before current date
            if(0 > entity.getEvaluationDeadline().compareTo(new Date()))
            {
                throw new ValidationException("[ERROR] Conference date cannot be set into the past");
            }
            // if evaluationDeadline is before proposalDeadline
            if(0 > entity.getEvaluationDeadline().compareTo(entity.getBiddingDeadline()))
            {
                throw new ValidationException("[ERROR] Conference evaluation deadline cannot be set before bidding deadline");
            }


            if(null == entity.getPresentationDeadline())
            {
                throw new ValidationException("[ERROR] Null conference date provided.");
            }
            // if presentationDeadline is before current date
            if(0 > entity.getPresentationDeadline().compareTo(new Date()))
            {
                throw new ValidationException("[ERROR] Conference date cannot be set into the past");
            }
            // if presentationDeadline is before evaluationDeadline
            if(0 > entity.getPresentationDeadline().compareTo(entity.getEvaluationDeadline()))
            {
                throw new ValidationException("[ERROR] Conference presentation deadline cannot be set before evaluation deadline");
            }
        }
    }
}
