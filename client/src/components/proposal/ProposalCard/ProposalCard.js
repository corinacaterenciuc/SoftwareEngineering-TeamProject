import React from 'react';
import './ProposalCard.css';
import {Card, StyledAction, StyledBody} from "baseui/card";
import {Button} from "baseui/button";
import {KIND, Tag, VARIANT} from "baseui/tag";
import {Icon} from '@iconify/react';
import cloudDownload from '@iconify/icons-carbon/cloud-download';
import {useDispatch, useSelector} from "react-redux";
import {UPDATE_CURRENT_PROPOSAL, UPDATE_CURRENT_REVIEW} from "../../../redux/context/contextActions";
import {Heading, HeadingLevel} from "baseui/heading";

const ProposalCard = (props) => {
    let {proposal, navProps, setModalOpen} = props;
    const {subsection} = navProps.match.params;

    let email = useSelector(state => state.auth.email);
    let reviews = useSelector(state => state.proposal.reviews);
    // TODO Get conference from redux
    let conference = useSelector(state =>
        state.conference.conferences.find(c => c.id === proposal.confereceId)
    );
    const dispatch = useDispatch();

    // Review
    // TODO Do this smarter
    let reviewsForProposal = [];
    let hasReviewed = false;
    let reviewIdx = -1;

    console.log(reviews);

    // Button logic
    let buttonText = '';
    switch (subsection) {
        case 'bidding':
            const hasBid = proposal.bidders.includes(email);
            buttonText = hasBid ? 'Cancel the bid' : 'Bid for proposal';
            break;
        case 'review':
            reviewsForProposal = reviews.filter(r => r.proposalId === proposal.id);
            console.log(reviewsForProposal);
            if (reviewsForProposal.length === 0) {
                hasReviewed = false
            } else {
                reviewIdx = reviewsForProposal.findIndex(r => r.reviewer === email);
                hasReviewed = reviewIdx !== -1;
            }
            buttonText = hasReviewed ? 'Edit Review' : 'Add Review';
            break;
        case 'resolve':
            buttonText = 'Resolve';
            break;
        default:
            console.assert(false, 'ProposalCard btn text switch reached');
            break;
    }

    return (
        <Card
            title={proposal.proposalName}
            overrides={{Root: {style: () => ({width: "50%"})}}}
        >
            <StyledBody>
                <HeadingLevel>
                    <Heading styleLevel={6}>{'My Conference Title'}</Heading>
                </HeadingLevel>
                <div>{proposal.abstract}</div>
                <div className={'ProposalExtraInfoContainer'}>
                    <div className={'ProposalTagsTopics'}>
                        <div className={'ProposalTopics'}>
                            {
                                proposal.topics.map((topic, idx) => (
                                    <Tag
                                        key={idx}
                                        closeable={false}
                                        kind={KIND.accent}
                                        variant={VARIANT.solid}>
                                        {topic}
                                    </Tag>
                                ))
                            }
                        </div>
                        <div className={'ProposalKeywords'}>
                            {
                                proposal.keywords.map((keyword, idx) => (
                                    <Tag
                                        key={idx + proposal.topics.length}
                                        closeable={false}
                                        kind={KIND.neutral}
                                        variant={VARIANT.solid}>
                                        {keyword}
                                    </Tag>
                                ))
                            }
                        </div>
                    </div>
                    <div className={'ProposalDownload'}>
                        <Icon width={'3em'} icon={cloudDownload}/>
                    </div>
                </div>
            </StyledBody>
            <StyledAction>
                <Button
                    overrides={{BaseButton: {style: {width: '100%'}}}}
                    onClick={() => {
                        dispatch({
                            type: UPDATE_CURRENT_PROPOSAL,
                            payload: {proposal: proposal}
                        });
                        if (reviewIdx !== -1) {
                            dispatch({
                                type: UPDATE_CURRENT_REVIEW,
                                payload: {review: reviewsForProposal[reviewIdx]}
                            })
                        }
                        setModalOpen(true);
                    }}>
                    {buttonText}
                </Button>
            </StyledAction>
        </Card>
    );
};

ProposalCard.propTypes = {};

ProposalCard.defaultProps = {};

export default ProposalCard;
