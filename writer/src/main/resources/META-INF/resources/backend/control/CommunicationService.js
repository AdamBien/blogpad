import { RESPONSE_ARRIVED,REQUEST_STARTED} from "../entity/CommunicationReducer.js";
import { store } from "../../store.js";

const responseArrived = (status,headers) => { 
    store.dispatch({
        type: RESPONSE_ARRIVED,
        payload: {
            status,
            headers
        }
    });
}

const requestStarted = () => { 
    store.dispatch({
        type:REQUEST_STARTED
    });
}

export const voidPost = async (uri, stringifiedBody) => {
    await voidRequest(uri, 'POST', stringifiedBody);
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
    const { headers } = response;    
    if (response.ok) {
        responseArrived(true,headers);
    } else { 
        responseArrived(false,headers);
        response;
    }
    return await response.json();
}
const voidBodylessRequest = async (uri,httpMethod) => { 
    requestStarted();
    const response = await fetch(uri, {
        method:httpMethod
    });
    const { headers } = response;
    if (response.ok) {
        responseArrived(true,headers);
    } else { 
        responseArrived(false,headers);
    }
}


const voidRequest = async (uri,httpMethod,body) => { 
    requestStarted();
    const response = await fetch(uri, {
        method: httpMethod,
        redirect: 'follow',
        headers: {
            "Content-type": "application/json"
        },
        body
    });
    const { headers } = response;
    if (response.ok) {
        responseArrived(true,headers);
    } else { 
        responseArrived(false,headers);
    }
}
