import { store } from "../../store.js";
import { NEW_POST_TEXT_CHANGED} from "../entity/PostReducer.js";
import { post } from "../../backend/control/CommunicationService.js";

export const textChanged = (name, value) => { 
    store.dispatch({
        type: NEW_POST_TEXT_CHANGED,
        payload: {
            name,
            value
        }
    })
}

export const save = async (newPost) => { 
    const stringified = JSON.stringify(newPost);
    post("http://localhost:8081/posts",stringified);
}

