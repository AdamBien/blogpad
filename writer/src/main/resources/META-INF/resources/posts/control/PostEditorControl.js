import { store } from "../../store.js";
import { EDITED_POST_SELECTED } from "../entity/PostReducer.js";

export const selectEditedPost = (postUniqueName) => { 
    const { posts: { list } } = store.getState();
    const editedPost = list.find(p => p.uniqueName === postUniqueName);
    store.dispatch({
        type: EDITED_POST_SELECTED,
        payload:editedPost
    });
}