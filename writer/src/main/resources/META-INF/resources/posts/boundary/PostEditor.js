import AirElement from "../../AirElement.js";
import { html } from "../../lib/lit-html.js";
import './NewPost.js';
import './PostList.js';
import { fetchAllPosts } from "../control/PostListService.js";
import { selectEditedPost } from "../control/PostEditorControl.js";

class PostEditor extends AirElement { 

    postConstruct() { 
        fetchAllPosts();
    }

    onAfterEnter(location, commands, router) { 
        selectEditedPost(location.params.post);
    }

    view() { 
        return html`
        <section>
            <b-newpost></b-newpost>
            <b-postlist></b-postlist>
        </section>
        `;
    }

}
customElements.define('b-posteditor',PostEditor);