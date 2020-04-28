import React from 'react';
import PropTypes from 'prop-types';
import './Evaluation.css';
import {Card} from "baseui/card";
import {StyledBody} from "baseui/card";
import {Checkbox} from "baseui/checkbox";

const Evaluation = (props) => {
    const [checked, setChecked] = React.useState(false);
    const gradeStyle = {
        color: props.colorScale[props.grade.gradeValue.toString()],
        fontWeight: "bold",
        wordWrap: "break-word",
        marginRight: "1em"
    };

    return (
        <Card className={"Evaluation " + (checked && "fade")} data-testid="Evaluation">
            <StyledBody style={{display: "flex"}}>
                <div style={{width: "90%"}}>
                    <p>{props.justification}</p>
                    <div style={{display: "flex"}}>
                        <small style={gradeStyle}>{props.grade.gradeText}</small>
                        {props.authorName
                            &&
                        <small style={{fontWeight: "bold"}}>{props.authorName}</small>
                        }
                    </div>
                </div>
                {props.displayCheckbox && <div style={{width: "10%", display: "flex", justifyContent: "center", alignItems: "center"}}>
                    <Checkbox checked={checked} onChange={e => setChecked(e.target.checked)}/>
                </div>}
            </StyledBody>
        </Card>
    )
};

Evaluation.propTypes = {
    justification: PropTypes.string.isRequired,
    grade: PropTypes.exact({
        gradeText: PropTypes.string.isRequired,
        gradeValue: PropTypes.number.isRequired
    }).isRequired,
    colorScale: PropTypes.exact({
        "-3": PropTypes.string.isRequired,
        "-2": PropTypes.string.isRequired,
        "-1": PropTypes.string.isRequired,
        "0": PropTypes.string.isRequired,
        "1": PropTypes.string.isRequired,
        "2": PropTypes.string.isRequired,
        "3": PropTypes.string.isRequired
    }).isRequired,
    /*
        Should be true only for the use case where the
        MainAuthor wants to mark the feedback as "being accounted for"
        Thus, this prop gets a default value of false - see below
     */
    displayCheckbox: PropTypes.bool,
    // Can be made null for use cases where the author must be anonymous
    authorName: PropTypes.string
};

Evaluation.defaultProps = {
    displayCheckbox: false
};

export default Evaluation;
