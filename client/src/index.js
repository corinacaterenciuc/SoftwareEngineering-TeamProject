import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import * as serviceWorker from './serviceWorker';
import {Client as Styletron} from 'styletron-engine-atomic';
import {Provider as StyletronProvider} from 'styletron-react';
import {LightTheme, BaseProvider} from 'baseui';
import {Provider} from "react-redux";
import store from './store.js'
import ReviewProposalModalDemo from "./components/ReviewProposalModalDemo/ReviewProposalModalDemo";

const engine = new Styletron();

ReactDOM.render(
    <Provider store={store}>
        <React.StrictMode>
          <StyletronProvider value={engine}>
            <BaseProvider theme={LightTheme}>
               <ReviewProposalModalDemo/>
            </BaseProvider>
          </StyletronProvider>
        </React.StrictMode>
    </Provider>,
    document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
