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

export const post = async (uri, stringifiedBody) => {
    voidRequest(uri,'POST',stringifiedBody);
}

export const put = async (uri, body) => {
    voidRequest(uri,'PUT',body);
}

export const get = async (uri) => { 
    return await bodylessRequest(uri,'GET');
}

export const deleteRequest = async (uri) => { 
    return await voidBodylessRequest(uri,'DELETE');
}

export const getAndDispatch = async (uri,actionType) => { 
    const payload = await get(uri);
    store.dispatch({
        type: actionType,
        payload
    });
}

const bodylessRequest = async (uri,httpMethod) => { 
    requestStarted();
    const response = await fetch(uri, {
        method:httpMethod
    });
    if (response.ok) {
        responseArrived(true);
    } else { 
        responseArrived(false);
        return;
    }
    return await response.json();
}
const voidBodylessRequest = async (uri,httpMethod) => { 
    requestStarted();
    const response = await fetch(uri, {
        method:httpMethod
    });
    if (response.ok) {
        responseArrived(true);
    } else { 
        responseArrived(false);
        return;
    }
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
