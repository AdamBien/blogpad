import { Router } from "./lib/@vaadin/router.js";
import './posts/boundary/PostEditor.js';
import './templates/boundary/EditTemplates.js';

const outlet = document.querySelector('output');
const router = new Router(outlet);
router.setRoutes([
  {path: '/',     component: 'b-posteditor'},
  {path: '/posts/:post',     component: 'b-posteditor'},
  {path: '/templates',  component: 'b-edittemplates'},
  {path: '(.*)', component: 'b-navigationerror'},
]);