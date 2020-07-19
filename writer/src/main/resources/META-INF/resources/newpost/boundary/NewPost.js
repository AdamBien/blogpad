import AirElement from "../../AirElement.js";
import { html } from "../../lib/lit-html.js";
import { textChanged } from "../control/NewPostControl.js";

class NewPost extends AirElement { 

    view() { 
        return html`
            <section>
            <form>
                <input requred placeholder="title" name="title" @keyup=${({target:{name,value}}) => textChanged(name,value)}>
                <textarea placeholder="content" name="content" @keyup=${({ target: { name, value } }) => textChanged(name,value)}></textarea>
                <button>create</button>
            </form>
            </section>
        `;
    }

    textChanged({ target: { name,value} }) { 
        console.log(name,value);        
    }


}
customElements.define('b-newpost',NewPost);