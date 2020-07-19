export const NEW_POST_TEXT_CHANGED = "NEW_POST_TEXT_CHANGED";
export const RESPONSE_ARRIVED = "RESPONSE_ARRIVED";
export const REQUEST_STARTED = "REQUEST_STARTED";

export const newPostReducer = (state = { editedPost: {}, communication: {}}, action) => { 
    const { type,payload } = action;
    switch (type) { 
        case NEW_POST_TEXT_CHANGED: { 
            return {
                ...state,
                editedPost: updateEditedPost(state, payload)
            }
        }
        case REQUEST_STARTED: { 
            return {
                ...state,
                communication: {
                    inProgress: true
                }
            }
        }
        case RESPONSE_ARRIVED: { 
            return {
                ...state,
                communication: {
                    inProgress: false,
                    success: payload
                }
            }
        }
            
    }

    return state;
}

const updateEditedPost = ({editedPost}, { name, value }) => { 
    const copied = Object.assign({}, editedPost);
    copied[name] = value;
    return copied;
}