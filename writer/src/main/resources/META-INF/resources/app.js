import { Router } from "./lib/@vaadin/router.js";
import './newpost/boundary/NewPost.js';
import './templates/boundary/EditTemplates.js';

const outlet = document.querySelector('output');
const router = new Router(outlet);
router.setRoutes([
  {path: '/',     component: 'b-newpost'},
  {path: '/templates',  component: 'b-edittemplates'},
  {path: '(.*)', component: 'b-navigationerror'},
]);