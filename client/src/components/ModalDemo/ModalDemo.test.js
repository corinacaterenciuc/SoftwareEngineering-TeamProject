import React from 'react';
import { cleanup, render } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import ModalDemo from './ModalDemo';

describe('<ModalDemo />', () => {
  afterEach(cleanup);

  test('it should mount', () => {
    const { getByTestId } = render(<ModalDemo />);
    const modalButton = getByTestId('ModalDemo');

    expect(modalButton).toBeInTheDocument();
  });
});