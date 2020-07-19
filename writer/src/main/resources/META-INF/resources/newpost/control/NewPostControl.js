import { store } from "../../store.js";
import { NEW_POST_TEXT_CHANGED } from "../entity/NewPostReducer.js";

export const textChanged = (name, value) => { 
    store.dispatch({
        type: NEW_POST_TEXT_CHANGED,
        payload: {
            name,
            value
        }
    })
}