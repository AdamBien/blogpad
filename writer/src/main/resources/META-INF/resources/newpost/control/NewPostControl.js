import { store } from "../../store.js";
import { NEW_POST_TEXT_CHANGED,RESPONSE_ARRIVED,REQUEST_STARTED} from "../entity/NewPostReducer.js";

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
    requestStarted();
    const response = await fetch("http://localhost:8080/posts", {
        method: "POST",
        body: JSON.stringify(post)
    });
    if (response.ok) {
        responseArrived(true);
    } else { 
        responseArrived(false);
    }
    
}

const responseArrived = (status) => { 
    store.dispatch({
        type: RESPONSE_ARRIVED,
        status
    });
}

const requestStarted = () => { 
    store.dispatch({
        type:REQUEST_STARTED
    });
}