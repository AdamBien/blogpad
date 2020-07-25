import { requestStarted,responseArrived } from "../../backend/control/CommunicationService.js";
import { store } from "../../store.js";
import { INITIAL_TEMPLATES_ARRIVED,TEMPLATE_SELECTED,TOGGLE_PREVIEW } from "../entity/EditTemplatesReducer.js";

export const selectTemplate = (list,templateName) => { 
    const selected = list.find(template => template.templateName === templateName);
    store.dispatch({
        type: TEMPLATE_SELECTED,
        payload:selected
    });
}

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

export const togglePreview = () => { 
    store.dispatch({
        type: TOGGLE_PREVIEW
    });
}


export const save = (editedTemplate) => { 

}
export const textChanged = (name, value) => { 
    
}