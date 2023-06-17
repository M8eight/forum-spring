let pathname = document.location.pathname;
let elements = document.getElementsByClassName("button-activity");

if(pathname.split("/")[1] == "topic") {
    elements[1].classList.add("active");
} else {
    for (let i = 0; i < elements.length; i++) {
        if(elements[i].pathname == pathname) {
            elements[i].classList.add("active");
        }
    }
}