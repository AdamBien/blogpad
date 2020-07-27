import { store } from "../../store.js";
import { NEW_POST_TEXT_CHANGED} from "../entity/PostReducer.js";
import { voidPost,voidPut } from "../../backend/control/CommunicationService.js";
import { fetchAllPosts } from "./PostListService.js";
import { selectEditedPost } from "./PostEditorControl.js";

export const textChanged = (name, value) => { 
    store.dispatch({
        type: NEW_POST_TEXT_CHANGED,
        payload: {
            name,
            value
        }
    })
}

const isEditedPostNew = _ => { 
    debugger
    const { posts: { editedPost } } = store.getState();
    return editedPost && !editedPost.createdAt;
}

export const save = async (newPost) => { 
    if (isEditedPostNew())
        create(newPost);
    else
        update(newPost);
}

const update = async (updatedPost) => { 
    const stringified = JSON.stringify(updatedPost);
    await voidPut("http://localhost:8081/posts", stringified);
    //use the unique name for the selection
    const uniqueName = updatedPost.uniqueName;
    //load all posts from server
    await fetchAllPosts();
    //replace the locally edited post with backend's version
    selectEditedPost(uniqueName);
}


const create = async (newPost) => { 
    const stringified = JSON.stringify(newPost);
    await voidPost("http://localhost:8081/posts", stringified);
    const { communication: { result: { headers } } } = store.getState()
    const uniqueName = headers.get('unique-name');
    //load all posts from server
    await fetchAllPosts();
    //identify the freshly created post with the unique name calacualted at the server
    //replace the locally edited post with backend's version
    selectEditedPost(uniqueName);
}

