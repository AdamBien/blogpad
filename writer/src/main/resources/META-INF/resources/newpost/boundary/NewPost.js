import AirElement from "../../AirElement.js";
import { html } from "../../lib/lit-html.js";

class NewPost extends AirElement { 

    view() { 
        return html`
            <section>
            <form>
                <input requred placeholder="title" name="title">
                <textarea placeholder="content" name="content"></textarea>
                <button>create</button>
            </form>
            </section>
        `;
    }


}
customElements.define('b-newpost',NewPost);