import { createStore,combineReducers } from "./lib/redux.js";
import { newPostReducer } from "./posts/entity/PostReducer.js";
import { editTemplatesReducer } from "./templates/entity/EditTemplatesReducer.js";
import { communicationReducer } from "./backend/entity/CommunicationReducer.js";

const root = combineReducers({
    posts: newPostReducer,
    templates: editTemplatesReducer,
    communication: communicationReducer
});
export const store = createStore(root, {}, window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__());