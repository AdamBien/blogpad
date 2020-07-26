import AirElement  from "../../AirElement.js";
import { html } from "../../lib/lit-html.js";

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

    post({ title,createdAt,modifiedAt }) { 
        return html`<li>${title}, ${createdAt} ${modifiedAt}</li>`;
    }

}

customElements.define('b-postlist',PostList);