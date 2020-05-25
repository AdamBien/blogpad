Handlebars.registerHelper("subtract", subtract);
Handlebars.registerHelper("add", add);
Handlebars.registerHelper("multiply", multiply);
Handlebars.registerHelper("divide", divide);

function subtract(lvalue, rvalue, options) {
    lvalue = parseFloat(lvalue);
    rvalue = parseFloat(rvalue);
    return lvalue - rvalue;
}
function add(lvalue, rvalue, options) {
    lvalue = parseFloat(lvalue);
    rvalue = parseFloat(rvalue);
    return lvalue - rvalue;
}
function multiply(lvalue, rvalue, options) {
    lvalue = parseFloat(lvalue);
    rvalue = parseFloat(rvalue);
    return lvalue * rvalue;
}
function divide(lvalue, rvalue, options) {
    lvalue = parseFloat(lvalue);
    rvalue = parseFloat(rvalue);
    return lvalue / rvalue;
}


const templateParameters = JSON.parse(parameterContent);
const compiledTemplate = Handlebars.compile(templateContent);
//the value of the last statement is returned to Java
compiledTemplate(templateParameters);
