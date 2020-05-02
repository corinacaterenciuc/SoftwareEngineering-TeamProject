import  PropTypes from "prop-types"

const conferenceType = PropTypes.exact({
    id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
    description: PropTypes.string.isRequired,
    zeroDeadline: PropTypes.instanceOf(Date).isRequired,
    abstractDeadline: PropTypes.instanceOf(Date).isRequired,
    proposalDeadline: PropTypes.instanceOf(Date).isRequired,
    biddingDeadline: PropTypes.instanceOf(Date).isRequired,
    evaluationDeadline: PropTypes.instanceOf(Date).isRequired,
    presentationDeadline: PropTypes.instanceOf(Date).isRequired

})

export {conferenceType}