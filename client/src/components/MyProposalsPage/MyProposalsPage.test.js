import React from 'react';
import { cleanup, render } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import MyProposalsPage from './MyProposalsPage';

describe('<MyProposalsPage />', () => {
  afterEach(cleanup);

  test('it should mount', () => {
    const { getByTestId } = render(<MyProposalsPage />);
    const myProposalsPage = getByTestId('MyProposalsPage');

    expect(myProposalsPage).toBeInTheDocument();
  });
});