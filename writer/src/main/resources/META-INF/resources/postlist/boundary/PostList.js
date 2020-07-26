import { AirElement } from "../../AirElement.js";

class PostList extends AirElement { 

    postConstruct() { 

    }

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