export const INITIAL_TEMPLATES_ARRIVED = "INITIAL_TEMPLATES_ARRIVED";
export const editTemplatesReducer = (state = {}, action) => { 
    const { type, payload } = action;
    switch (type) { 
        case INITIAL_TEMPLATES_ARRIVED: { 
            const templates = Object.assign({}, payload);
            return {
                ...state,
                list:templates
            }
        }
    }

    return state;
}