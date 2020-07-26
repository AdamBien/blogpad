import AirElement  from "../../AirElement.js";
import { html } from "../../lib/lit-html.js";
class PostList extends AirElement { 

    view() { 
        return html`
            <ol>
                <li>first</li>
            </ol>
        `;
    }

    post({ title }) { 
        return html`<li>${title}</li>`;
    }

}

customElements.define('b-postlist',PostList);