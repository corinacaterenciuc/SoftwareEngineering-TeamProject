import React from 'react';
import PropTypes from 'prop-types';
import './ProposalSection.css';

const ProposalSection = () => (
  <div className="ProposalSection" data-testid="ProposalSection">
    <div className={"ProposalSectionNavigation"}></div>
    <div className={"ProposalSectionContent"}></div>
  </div>
);

ProposalSection.propTypes = {};

ProposalSection.defaultProps = {};

export default ProposalSection;
