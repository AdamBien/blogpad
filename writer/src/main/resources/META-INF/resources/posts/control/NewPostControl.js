import { store } from "../../store.js";
import { NEW_POST_TEXT_CHANGED} from "../entity/PostReducer.js";
import { voidPost } from "../../backend/control/CommunicationService.js";
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

export const save = async (newPost) => { 
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

