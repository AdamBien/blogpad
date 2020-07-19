import { createStore,combineReducers } from "./lib/redux.js";
import { newPostReducer } from "./newpost/entity/NewPostReducer.js";

const root = combineReducers({posts:newPostReducer});
export const store = createStore(root, {}, window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__());