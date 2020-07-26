import AirElement  from "../../AirElement.js";
import { html } from "../../lib/lit-html.js";
import { deletePost } from "../control/PostListService.js";

class PostList extends AirElement { 

    extractState({ posts: { list } }) { 
        return list;
    }



    view() { 
        return html`
            <ol>
               ${this.state.map(p => this.post(p))}
            </ol>
        `;
    }

    post({ title,uniqueName,createdAt,modifiedAt }) { 
    return html`<li><a href="/posts/${uniqueName}">${title}</a>, ${createdAt} ${modifiedAt} <button class="button--delete" @click="${e => deletePost(uniqueName)}">delete</button></li>`;
    }

}

customElements.define('b-postlist',PostList);