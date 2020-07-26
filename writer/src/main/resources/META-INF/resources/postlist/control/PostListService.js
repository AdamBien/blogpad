import { get } from "../../backend/control/CommunicationService.js";
const fetchAllPosts = async _ => { 
    const list = await fetchAllPosts('http://localhost:8081/posts');
    
}