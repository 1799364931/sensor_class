function toggleMenu(element) {
    let submenu = element.nextElementSibling;

    if (submenu && submenu.classList.contains("submenu")) {
        submenu.style.display = submenu.style.display === "block" ? "none" : "block";
    }
}
