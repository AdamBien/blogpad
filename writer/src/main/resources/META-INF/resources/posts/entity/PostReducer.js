export const NEW_POST_TEXT_CHANGED = "NEW_POST_TEXT_CHANGED";
export const ALL_POSTS_FETCHED = "ALL_POSTS_FETCHED";

export const newPostReducer = (state = { editedPost: {},list:[]}, action) => { 
    const { type } = action;
    switch (type) { 
        case NEW_POST_TEXT_CHANGED:
            newPostTextChanged(state, action);
        case ALL_POSTS_FETCHED:
            allPostsFetched(state,action);
    }
    return state;
}

const allPostsFetched = (state, { payload }) => { 
    return {
        ...state,
        list: payload
    }
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