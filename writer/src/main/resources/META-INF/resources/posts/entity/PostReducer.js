export const NEW_POST_TEXT_CHANGED = "NEW_POST_TEXT_CHANGED";
export const ALL_POSTS_FETCHED = "ALL_POSTS_FETCHED";
export const EDITED_POST_SELECTED = "EDITED_POST_SELECTED";

export const newPostReducer = (state = { editedPost: {},list:[]}, action) => { 
    const { type } = action;
    switch (type) { 
        case NEW_POST_TEXT_CHANGED:
            return newPostTextChanged(state, action);
        case ALL_POSTS_FETCHED:
            return allPostsFetched(state, action);
        case EDITED_POST_SELECTED:
            return setEditedPost(state, action);
    }
    return state;
}

const allPostsFetched = (state, { payload: { posts } }) => { 
    return {
        ...state,
        list: posts
    }
}

const newPostTextChanged = (state, { payload }) => { 
    return {
        ...state,
        editedPost: updateEditedPost(state, payload)
    }
}

const setEditedPost = (state, { payload }) => { 
    return {
        ...state,
        editedPost: Object.assign({}, payload)
    }
}

const updateEditedPost = ({editedPost}, { name, value }) => { 
    const copied = Object.assign({}, editedPost);
    copied[name] = value;
    return copied;
}

