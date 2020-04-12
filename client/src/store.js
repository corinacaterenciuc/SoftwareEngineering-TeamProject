import {applyMiddleware, compose, createStore} from "redux";
import thunk from "react-thunk";
import rootReducer from "./reducers/index"

const store = createStore(
    rootReducer,
    {},
    compose(
        applyMiddleware(thunk),
        window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
    )
);

export default store;