import { requestStarted,responseArrived } from "../../backend/control/CommunicationService.js";
import { store } from "../../store.js";
import { INITIAL_TEMPLATES_ARRIVED } from "../entity/EditTemplatesReducer.js";

export const loadInitialState = async () => { 
    requestStarted();
    try {
        const response = await fetch("http://localhost:8081/templates")
        const payload = await response.json();
        store.dispatch({
            type: INITIAL_TEMPLATES_ARRIVED,
            payload
        });
    } catch (error) { 
        responseArrived(false);
    }
    responseArrived(true);
}


export const save = (editedTemplate) => { 

}
export const textChanged = (name, value) => { 
    
}