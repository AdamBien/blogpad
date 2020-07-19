import AirElement from "../../AirElement.js";
import { html } from "../../lib/lit-html.js";

class NewPost extends AirElement { 

    view() { 
        return html`
            <section>
                hello
            </section>
        `;
    }
}
customElements.define('b-newpost',NewPost);