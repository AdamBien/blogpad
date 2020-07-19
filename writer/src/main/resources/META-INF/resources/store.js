import { createStore } from "./lib/redux.js";

const emptyReducer = (action, state) => state;

export const store = createStore(emptyReducer, {});