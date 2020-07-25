import { store } from "../../store.js";
import { NEW_POST_TEXT_CHANGED} from "../entity/NewPostReducer.js";
import { requestStarted, responseArrived } from "../../backend/control/CommunicationService.js";

export const textChanged = (name, value) => { 
    store.dispatch({
        type: NEW_POST_TEXT_CHANGED,
        payload: {
            name,
            value
        }
    })
}

export const save = async (post) => { 
    debugger
    requestStarted();
    const response = await fetch("http://localhost:8081/posts", {
        method: "POST",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(post)
    });
    if (response.ok) {
        responseArrived(true);
    } else { 
        responseArrived(false);
    }
}

