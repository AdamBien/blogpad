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
    request(uri,'POST',body);
}

export const put = async (uri, body) => {
    request(uri,'PUT',body);
}


const request = async (uri,httpMethod,body) => { 
    debugger
    requestStarted();
    const response = await fetch(uri, {
        method: httpMethod,
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(body)
    });
    if (response.ok) {
        responseArrived(true);
    } else { 
        responseArrived(false);
    }
}
