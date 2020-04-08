import React from 'react';
import { cleanup, render } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import AddConferenceModal from './AddConferenceModal';

describe('<AddConferenceModal />', () => {
  afterEach(cleanup);

  test('it should mount', () => {
    const { getByTestId } = render(<AddConferenceModal />);
    const conferenceModal = getByTestId('AddConferenceModal');

    expect(conferenceModal).toBeInTheDocument();
  });
});