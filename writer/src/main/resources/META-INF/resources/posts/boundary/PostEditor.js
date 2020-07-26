import AirElement from "../../AirElement.js";
import { html } from "../../lib/lit-html.js";
import './NewPost.js';
import './PostList.js';
import { fetchAllPosts } from "../control/PostListService.js";

class PostEditor extends AirElement { 

    postConstruct() { 
        fetchAllPosts();
    }

    view() { 
        return html`
            <b-newpost></b-newpost>
            <b-postlist></b-postlist>
        `;
    }

}
customElements.define('b-posteditor',PostEditor);