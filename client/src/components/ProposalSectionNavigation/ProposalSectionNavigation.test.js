import React from 'react';
import { cleanup, render } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import ProposalSectionNavigation from './ProposalSectionNavigation';

describe('<ProposalSectionNavigation />', () => {
  afterEach(cleanup);

  test('it should mount', () => {
    const { getByTestId } = render(<ProposalSectionNavigation />);
    const proposalSectionNavigation = getByTestId('ProposalSectionNavigation');

    expect(proposalSectionNavigation).toBeInTheDocument();
  });
});