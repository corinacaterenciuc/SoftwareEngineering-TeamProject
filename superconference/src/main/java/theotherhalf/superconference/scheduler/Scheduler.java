package theotherhalf.superconference.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import theotherhalf.superconference.domain.*;
import theotherhalf.superconference.services.*;

import java.util.Date;
import java.util.List;

@Component
public class Scheduler
{
    @Autowired
    private UserService userService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private ProposalService proposalService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SectionService sectionService;

    @Scheduled(cron = "0 0/3 * * * *")
    public void scheduleFixedRateTask()
    {
        System.out.println("A minute has passed");
    }

    //@Scheduled(cron="0 1 1 * * *")
    @Scheduled(cron = "0 0/3 * * * *")
    public void removeRejectedProposals()
    {
        boolean shouldReject = false;
        boolean hasPositive = false;
        boolean hasNegative = false;
        List<Conference> conferenceList = this.conferenceService.getAll();
        for(Conference conference : conferenceList)
        {
            if(0 > conference.getEvaluationDeadline().compareTo(new Date()))
            {
                List<Proposal> proposalList = conference.getDefaultSection().getProposals();
                for(Proposal proposal : proposalList)
                {
                    shouldReject = false;
                    hasPositive = false;
                    hasNegative = false;
                    int reviewers = proposal.getReviewers().size();
                    List<Review> reviewList = proposal.getReviews();
                    for (Review review : reviewList)
                    {
                        if(null == review)
                        {
                            continue;
                        }
                        if(null != review.getGrade() && review.getGrade().getGrade() < 0)
                        {
                            hasNegative = true;
                        }
                        else if(null != review.getGrade() && review.getGrade().getGrade() >= 0)
                        {
                            hasPositive = true;
                        }
                    }
                    if(hasNegative && !hasPositive)
                    {
                        this.proposalService.removeProposal(conference.getID(), proposal.getID());
                    }
                }
            }
        }
    }

}