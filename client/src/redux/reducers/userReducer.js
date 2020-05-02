import {PCM, SCM, USER} from "../../userClaims";

const initialState = {
    id: 1,
    name: 'Katrina Madara',
    claims: [USER, PCM, SCM],
    email: 'kati.mada@gmail.com'
};

export default function(state = initialState, action)
{
    return state;
}
