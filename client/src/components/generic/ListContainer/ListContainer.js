import React from 'react';
import './ProposalList.css';
import ProposalCard from "../ProposalCard/ProposalCard";
import {Heading, HeadingLevel} from "baseui/heading";

const ProposalList = (props) =>
{
    const { match } = props;
    const subsection = match.params.subsection;
    const toTitle = (subsectionName) => subsectionName
        .split('-')
        .map(w => w.charAt(0).toUpperCase() + w.slice(1))
        .join(' ');

    return (
        <div className="ProposalList">
            <HeadingLevel>
                <Heading>{toTitle(subsection)}</Heading>
                {props.children}
            </HeadingLevel>
        </div>
    );
};

ProposalList.propTypes = {};

ProposalList.defaultProps = {};

export default ProposalList;
