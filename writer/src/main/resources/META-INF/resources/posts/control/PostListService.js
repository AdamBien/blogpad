import { getAndDispatch } from "../../backend/control/CommunicationService.js";
import { ALL_POSTS_FETCHED } from "../entity/PostReducer.js";
export const fetchAllPosts = async _ => { 
    await getAndDispatch('http://localhost:8081/posts', ALL_POSTS_FETCHED);
}