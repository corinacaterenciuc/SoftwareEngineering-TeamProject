import { combineReducers} from "redux";
import conferenceReducer from './conferenceReducer';

export default combineReducers({
    conference: conferenceReducer,
})
