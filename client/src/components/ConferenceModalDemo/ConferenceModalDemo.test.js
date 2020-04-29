import React from 'react';
import { cleanup, render } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import ConferenceModalDemo from './ConferenceModalDemo';

describe('<ConferenceModalDemo />', () => {
  afterEach(cleanup);

  test('it should mount', () => {
    const { getByTestId } = render(<ConferenceModalDemo />);
    const conferenceModalDemo = getByTestId('ConferenceModalDemo');

    expect(conferenceModalDemo).toBeInTheDocument();
  });
});