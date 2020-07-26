export const NEW_POST_TEXT_CHANGED = "NEW_POST_TEXT_CHANGED";

export const newPostReducer = (state = { editedPost: {}}, action) => { 
    const { type } = action;
    switch (type) { 
        case NEW_POST_TEXT_CHANGED:
            newPostTextChanged(state, action);
    }
    return state;
}

const newPostTextChanged = (state, { payload }) => { 
    return {
        ...state,
        editedPost: updateEditedPost(state, payload)
    }

}

const updateEditedPost = ({editedPost}, { name, value }) => { 
    const copied = Object.assign({}, editedPost);
    copied[name] = value;
    return copied;
}