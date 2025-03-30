document.addEventListener('DOMContentLoaded', function() {
    const menuIcon = document.querySelector('.nav-submenu-icon');
    const dropdownMenu = document.querySelector('.dropdown-menu');

    menuIcon.addEventListener('click', function(event) {
        event.preventDefault();
        this.classList.toggle('active');
        dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
    });
    
    document.addEventListener('click', function(event) {
        if (!menuIcon.contains(event.target) && !dropdownMenu.contains(event.target)) {
            dropdownMenu.style.display = 'none';
            menuIcon.classList.remove('active');
        }
    });
});