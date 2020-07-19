import { createStore } from "./lib/redux.js";
import { newPostReducer } from "./newpost/entity/NewPostReducer.js";


export const store = createStore(newPostReducer, {}, window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__());