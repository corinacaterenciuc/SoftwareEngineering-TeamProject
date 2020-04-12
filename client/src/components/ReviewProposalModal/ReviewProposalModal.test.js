import React from 'react';
import { cleanup, render } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import ReviewProposalModal from './ReviewProposalModal';

describe('<ReviewProposalModal />', () => {
  afterEach(cleanup);

  test('it should mount', () => {
    const { getByTestId } = render(<ReviewProposalModal />);
    const reviewProposalModal = getByTestId('ReviewProposalModal');

    expect(reviewProposalModal).toBeInTheDocument();
  });
});