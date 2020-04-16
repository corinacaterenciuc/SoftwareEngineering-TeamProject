import {FETCH_CONFERENCES, NEW_CONFERENCE} from "./types";

export const fetchConferences = () => dispatch =>
{
        // GET request for Conferences goes here.
        /**
         * .......
         * .then(result => result.json())
         * .then(conferences => dispatch({
         *    type: FETCH_CONFERENCES,
         *    payload: conferences
         *  )});
         */
};

export const createConference = (postData) => dispatch =>
{
        // POST request for new Conference goes here.
        /**
         * .......
         * .then(result => result.json())
         * .then(conference => dispatch({
         *    type: NEW_CONFERENCE,
         *    payload: conference
         *  )});
         */
};