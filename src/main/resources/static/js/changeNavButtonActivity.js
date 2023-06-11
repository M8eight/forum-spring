let pathname = document.location.pathname.split("/")[1];
let elements = document.getElementsByClassName("button-activity");

if(pathname == "topic") {
    elements[1].classList.add("active");
} else {
    for (let i = 0; i < elements.length; i++) {
        if(elements[i].pathname.split("/")[1] == pathname) {
            elements[i].classList.add("active");
        }
    }
}