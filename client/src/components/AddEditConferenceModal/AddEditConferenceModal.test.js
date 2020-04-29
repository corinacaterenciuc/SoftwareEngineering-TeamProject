import React from 'react';
import { cleanup, render } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import AddEditConferenceModal from './AddEditConferenceModal';

describe('<AddEditConferenceModal />', () => {
  afterEach(cleanup);

  test('it should mount', () => {
    const { getByTestId } = render(<AddEditConferenceModal />);
    const conferenceModal = getByTestId('AddEditConferenceModal');

    expect(conferenceModal).toBeInTheDocument();
  });
});