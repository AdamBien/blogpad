import { store } from "../../store.js";
import { INITIAL_TEMPLATES_ARRIVED,TEMPLATE_SELECTED,TOGGLE_PREVIEW,TEMPLATE_TEXT_CHANGED } from "../entity/EditTemplatesReducer.js";
import { get } from "../../backend/control/CommunicationService.js";

export const selectTemplate = (list,templateName) => { 
    const selected = list.find(template => template.templateName === templateName);
    store.dispatch({
        type: TEMPLATE_SELECTED,
        payload:selected
    });
}

export const loadInitialState = async () => { 
        const payload = await get("http://localhost:8081/templates");
        store.dispatch({
            type: INITIAL_TEMPLATES_ARRIVED,
            payload
        });
}

export const togglePreview = () => { 
    store.dispatch({
        type: TOGGLE_PREVIEW
    });
}


export const save = (editedTemplate) => { 
        put("http://localhost:8081/templates",editedTemplate);
}
export const textChanged = (name, value) => { 
    store.dispatch({
        type: TEMPLATE_TEXT_CHANGED,
        payload: {
            name,
            value
        }
    })
    
}