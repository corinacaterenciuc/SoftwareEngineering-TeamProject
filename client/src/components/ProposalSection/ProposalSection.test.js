import React from 'react';
import { cleanup, render } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import ProposalSection from './ProposalSection';

describe('<ProposalSection />', () => {
  afterEach(cleanup);

  test('it should mount', () => {
    const { getByTestId } = render(<ProposalSection />);
    const proposalSection = getByTestId('ProposalSection');

    expect(proposalSection).toBeInTheDocument();
  });
});