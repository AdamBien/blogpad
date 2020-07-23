export const INITIAL_TEMPLATES_ARRIVED = "INITIAL_TEMPLATES_ARRIVED";
export const TEMPLATE_SELECTED = "TEMPLATE_SELECTED";

export const editTemplatesReducer = (state = { list: [], editedTemplate: {}}, action) => { 
    const { type, payload } = action;
    switch (type) { 
        case INITIAL_TEMPLATES_ARRIVED: { 
            return {
                ...state,
                list:payload
            }
        }
        case TEMPLATE_SELECTED: { 
            return {
                ...state,
                editedTemplate: Object.assign({},payload)
            }
        }
    }

    return state;
}