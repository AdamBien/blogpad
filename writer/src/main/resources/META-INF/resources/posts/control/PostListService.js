import { getAndDispatch, deleteRequest } from "../../backend/control/CommunicationService.js";
import { ALL_POSTS_FETCHED } from "../entity/PostReducer.js";
export const fetchAllPosts = async _ => { 
   return await getAndDispatch('http://localhost:8081/posts', ALL_POSTS_FETCHED);
}

export const deletePost = async (title) => { 
    console.log('title: ' + title);
    await deleteRequest(`http://localhost:8081/posts/${title}`);
    fetchAllPosts();
}