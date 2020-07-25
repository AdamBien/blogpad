import { RESPONSE_ARRIVED,REQUEST_STARTED} from "../entity/CommunicationReducer.js";
import { store } from "../../store.js";

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

export const post = async (uri, body) => {
    voidRequest(uri,'POST',body);
}

export const put = async (uri, body) => {
    voidRequest(uri,'PUT',body);
}

export const get = async (uri) => { 
    return await bodylessRequest(uri);
}

const bodylessRequest = async (uri) => { 
    const response = await fetch(uri);
    if (response.ok) {
        responseArrived(true);
    } else { 
        responseArrived(false);
        return;
    }
    return await response.json();
}


const voidRequest = async (uri,httpMethod,body) => { 
    requestStarted();
    const response = await fetch(uri, {
        method: httpMethod,
        headers: {
            "Content-type": "application/json"
        },
        body
    });
    if (response.ok) {
        responseArrived(true);
    } else { 
        responseArrived(false);
    }
}
