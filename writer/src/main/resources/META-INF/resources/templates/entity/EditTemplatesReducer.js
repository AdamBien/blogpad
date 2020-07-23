export const INITIAL_TEMPLATES_ARRIVED = "INITIAL_TEMPLATES_ARRIVED";
export const editTemplatesReducer = (state = {list:[]}, action) => { 
    const { type, payload } = action;
    switch (type) { 
        case INITIAL_TEMPLATES_ARRIVED: { 
            return {
                ...state,
                list:payload
            }
        }
    }

    return state;
}