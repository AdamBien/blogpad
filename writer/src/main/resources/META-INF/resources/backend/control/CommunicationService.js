import { RESPONSE_ARRIVED,REQUEST_STARTED} from "../entity/CommunicationReducer.js";
import { store } from "../../store.js";

export const responseArrived = (status) => { 
    store.dispatch({
        type: RESPONSE_ARRIVED,
        status
    });
}

export const requestStarted = () => { 
    store.dispatch({
        type:REQUEST_STARTED
    });
}