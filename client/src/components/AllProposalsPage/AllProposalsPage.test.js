import React from 'react';
import { cleanup, render } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import AllProposalsPage from './AllProposalsPage';

describe('<AllProposalsPage />', () => {
  afterEach(cleanup);

  test('it should mount', () => {
    const { getByTestId } = render(<AllProposalsPage />);
    const allProposalsPage = getByTestId('AllProposalsPage');

    expect(allProposalsPage).toBeInTheDocument();
  });
});