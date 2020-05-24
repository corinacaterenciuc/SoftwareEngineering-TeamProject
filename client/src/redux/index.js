import {combineReducers} from "redux";
import conferenceReducer from './conference/conferenceReducer';
import proposalReducer from "./proposal/proposalReducer";
import userReducer from "./user/userReducer";
import authenticationReducer from "./auth/authenticationReducer";
import notificationReducer from "./notification/notificationReducer";

export default combineReducers({
    conference: conferenceReducer,
    proposal: proposalReducer,
    notification: notificationReducer,
    user: userReducer,
    auth: authenticationReducer
})
