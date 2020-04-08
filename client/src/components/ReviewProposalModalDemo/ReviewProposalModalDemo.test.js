import React from 'react';
import { cleanup, render } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import ReviewProposalModalDemo from './ReviewProposalModalDemo';

describe('<ReviewProposalModalDemo />', () => {
  afterEach(cleanup);

  test('it should mount', () => {
    const { getByTestId } = render(<ReviewProposalModalDemo />);
    const reviewProposalModelDemo = getByTestId('ReviewProposalModalDemo');

    expect(reviewProposalModelDemo).toBeInTheDocument();
  });
});