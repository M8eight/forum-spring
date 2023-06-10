let pathname = document.location.pathname;
let elements = document.getElementsByClassName("button-activity");

for (let i = 0; i < elements.length; i++) {
    if(elements[i].pathname == pathname) {
        elements[i].classList.add("active");
        console.log(elements[i].pathname);
    }
}
console.log(pathname);

//todo Сделеть что бы работал