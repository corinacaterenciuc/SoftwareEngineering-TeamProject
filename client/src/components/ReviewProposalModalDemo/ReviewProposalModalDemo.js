import React from 'react';
import './ReviewProposalModalDemo.css';
import ReviewProposalModal from "../ReviewProposalModal/ReviewProposalModal";
import {Button} from "baseui/button";

const ReviewProposalModalDemo = () => {

    const [isOpen, setIsOpen] = React.useState(false);

    return (
        <div className="ReviewProposalModalDemo" data-testid="ReviewProposalModalDemo">
            <h1>ReviewProposalModalDemo</h1>
            <ReviewProposalModal
                isOpen={isOpen}
                setIsOpen={setIsOpen}
                proposal={{
                    title: 'A Cure for Depression',
                    abstract: 'Our study indicates strong correlation with dank memes.',
                    proposal_url: 'foo.pdf'
                }}
            />
            <Button onClick={() => setIsOpen(true)}>Open</Button>
        </div>
    );
};

ReviewProposalModalDemo.propTypes = {};

ReviewProposalModalDemo.defaultProps = {};

export default ReviewProposalModalDemo;
