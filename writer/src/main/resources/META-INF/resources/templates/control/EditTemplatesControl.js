import { store } from "../../store.js";
import { INITIAL_TEMPLATES_ARRIVED,TEMPLATE_SELECTED,TOGGLE_PREVIEW,TEMPLATE_TEXT_CHANGED } from "../entity/EditTemplatesReducer.js";
import { voidPut, getAndDispatch } from "../../backend/control/CommunicationService.js";

export const selectTemplate = (list,templateName) => { 
    const selected = list.find(template => template.templateName === templateName);
    store.dispatch({
        type: TEMPLATE_SELECTED,
        payload:selected
    });
}

export const loadInitialState = async () => { 
        getAndDispatch("http://localhost:8081/templates",INITIAL_TEMPLATES_ARRIVED);
}

export const togglePreview = () => { 
    store.dispatch({
        type: TOGGLE_PREVIEW
    });
}

export const save = (editedTemplate) => { 
    const { templateName,content } = editedTemplate;
    const uri = `http://localhost:8081/templates/${templateName}`;
    voidPut(uri, content);
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