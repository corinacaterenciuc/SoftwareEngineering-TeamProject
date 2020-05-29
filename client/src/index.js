import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import * as serviceWorker from './serviceWorker';
import {Client as Styletron} from 'styletron-engine-atomic';
import {Provider as StyletronProvider} from 'styletron-react';
import {BaseProvider, LightTheme} from 'baseui';
import {BrowserRouter} from "react-router-dom";
import store from "./redux/store";
import {Provider} from "react-redux";
import RootNavigation from "./components/navigation/RootNavigation/RootNavigation";

const engine = new Styletron();

ReactDOM.render(
    <Provider store={store}>
        <StyletronProvider value={engine}>
            <BaseProvider theme={LightTheme}>
                <BrowserRouter>
                    <RootNavigation/>
                </BrowserRouter>
            </BaseProvider>
        </StyletronProvider>
    </Provider>,
    document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
