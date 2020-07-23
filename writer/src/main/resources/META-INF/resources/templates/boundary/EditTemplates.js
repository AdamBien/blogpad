import AirElement from "../../AirElement.js";
import { html } from "../../lib/lit-html.js";
import { textChanged,save, loadInitialState,selectTemplate } from "../control/EditTemplatesControl.js";

class EditTemplates extends AirElement { 

    postConstruct() { 
        loadInitialState();
    }

    extractState({ templates }) { 
        return templates;
    }

    view() { 
        const { list,editedTemplate } = this.state;
        return html`
            <section>
            <form>
                <select name="name" @input="${({ target: { value } }) => selectTemplate(list,value)}">
                  <option value="" disabled selected hidden>templates...</option>
                   ${list.map(template => template.templateName).
                        map(name => html`<option value="${name}">${name}</option>`)}
                </select>
                <textarea .value=${editedTemplate.content} placeholder="content" name="content" @keyup=${({ target: { name, value } }) => textChanged(name, value)}>
                </textarea>
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