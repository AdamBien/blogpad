import { createStore,combineReducers } from "./lib/redux.js";
import { newPostReducer } from "./newpost/entity/NewPostReducer.js";
import { editTemplatesReducer } from "./templates/entity/EditTemplatesReducer.js";

const root = combineReducers({
    posts: newPostReducer,
    templates: editTemplatesReducer
});
export const store = createStore(root, {}, window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__());