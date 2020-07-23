import AirElement from "../../AirElement.js";
import { html } from "../../lib/lit-html.js";
import { textChanged,save, loadInitialState } from "../control/EditTemplatesControl.js";

class EditTemplates extends AirElement { 

    postConstruct() { 
        loadInitialState();
    }

    view() { 
        return html`
            <section>
            <form>
                <input requred placeholder="name" name="name" @keyup=${({target:{name,value}}) => textChanged(name,value)}>
                <textarea placeholder="content" name="content" @keyup=${({ target: { name, value } }) => textChanged(name,value)}></textarea>
                <button @click="${e => this.save(e)}">create</button>
            </form>
            </section>
        `;
    }

    save(e) { 
        e.preventDefault();
        const { editedPost } = this.state;
        save(editedPost);
    }

    textChanged({ target: { name,value} }) { 
        console.log(name,value);        
    }


}
customElements.define('b-edittemplates',EditTemplates);