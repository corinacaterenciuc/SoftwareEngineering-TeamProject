import React from 'react';
import { cleanup, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import Evaluation from "./Evaluation";

describe('<Evaluation />', () => {
    afterEach(cleanup);

    test('it should mount', () => {
        const { getByTestId } = render(<Evaluation
            justification="Hello World"
            grade={{gradeText: "Strong Accept", gradeValue: 3}}
            colorScale={{
                '-3': '#ff0000',
                '-2': '#fb5600',
                '-1': '#ee8200',
                '0' : '#d7a700',
                '1' : '#b6c700',
                '2' : '#86e400',
                '3' : '#00ff00'
            }}
        />);
        const evaluation = getByTestId('Evaluation');

        expect(evaluation).toBeInTheDocument();
    });

    test('it should display the justification', () => {
        render(<Evaluation
            justification="Hello World"
            grade={{gradeText: "Strong Accept", gradeValue: 3}}
            colorScale={{
                '-3': '#ff0000',
                '-2': '#fb5600',
                '-1': '#ee8200',
                '0' : '#d7a700',
                '1' : '#b6c700',
                '2' : '#86e400',
                '3' : '#00ff00'
            }}
        />);

        expect(screen.getByText('Hello World')).toBeInTheDocument();
    })

    test('grade text should be coloured accordingly', () => {
        render(<Evaluation
            justification="Hello World"
            grade={{gradeText: "Strong Accept", gradeValue: 3}}
            colorScale={{
                '-3': '#ff0000',
                '-2': '#fb5600',
                '-1': '#ee8200',
                '0' : '#d7a700',
                '1' : '#b6c700',
                '2' : '#86e400',
                '3' : '#00ff00'
            }}
        />);

        expect(screen.getByText('Strong Accept').style.color).toBe('rgb(0, 255, 0)');
    })
});