export const NEW_POST_TEXT_CHANGED = "NEW_POST_TEXT_CHANGED";

export const newPostReducer = (state = { editedPost: {}}, action) => { 
    const { type,payload } = action;
    switch (type) { 
        case NEW_POST_TEXT_CHANGED: { 
            return {
                ...state,
                editedPost: updateEditedPost(state, payload)
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