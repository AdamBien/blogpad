import { getAndDispatch } from "../../backend/control/CommunicationService.js";
const fetchAllPosts = async _ => { 
    getAndDispatch('http://localhost:8081/posts',)

}