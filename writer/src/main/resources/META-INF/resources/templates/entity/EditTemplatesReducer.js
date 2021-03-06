
export const INITIAL_TEMPLATES_ARRIVED = "INITIAL_TEMPLATES_ARRIVED";
export const TEMPLATE_SELECTED = "TEMPLATE_SELECTED";
export const TOGGLE_PREVIEW = "TOGGLE_PREVIEW";
export const TEMPLATE_TEXT_CHANGED = "TEMPLATE_TEXT_CHANGED";

export const editTemplatesReducer = (state = { list: [], editedTemplate: {content:'',previewMode:false}}, action) => { 
    const { type } = action;
    switch (type) { 
        case INITIAL_TEMPLATES_ARRIVED: 
            return initialTemplatesArrived(state, action);
        case TEMPLATE_SELECTED: 
            return templateSelected(state,action);
        case TOGGLE_PREVIEW: 
            return togglePreview(state, action);        
        case TEMPLATE_TEXT_CHANGED:
            return templateTextChanged(state,action);
            
    }

    return state;
}
const togglePreview = (state, action) => {
    const { editedTemplate } = state;
    const copy = Object.assign({}, editedTemplate, { previewMode: true });
    return {
        ...state,
        editedTemplate: copy
    }
}


const templateSelected = (state, { payload }) => { 
    return {
        ...state,
        editedTemplate: Object.assign({},payload)
    }
}

const initialTemplatesArrived = (state, {payload}) => { 
    return {
        ...state,
        list:payload
    }
}

const templateTextChanged = (state, { payload }) => { 
    return {
        ...state,
        editedTemplate: updateEditedTemplate(state,payload)
    }

}

const updateEditedTemplate = ({ editedTemplate },{ name, value }) => { 
    const copied = Object.assign({}, editedTemplate);
    copied[name] = value;
    return copied;
}