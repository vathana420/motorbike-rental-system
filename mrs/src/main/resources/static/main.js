/* ============================================
   MotoRent — Main JavaScript
   Place in: src/main/resources/static/js/main.js
   ============================================ */

/* ============================================
   BOOKING PRICE CALCULATOR
   Used in: customer/book.html
   ============================================ */

function initBookingCalculator(pricePerDay) {
    const startInput = document.getElementById('startDate');
    const endInput   = document.getElementById('endDate');
    const totalEl    = document.getElementById('totalPrice');
    const daysEl     = document.getElementById('dayCount');

    if (!startInput || !endInput) return;

    function calculate() {
        const start = new Date(startInput.value);
        const end   = new Date(endInput.value);

        if (startInput.value && endInput.value && end > start) {
            const days  = Math.ceil((end - start) / (1000 * 60 * 60 * 24));
            const total = (days * pricePerDay).toFixed(2);

            if (totalEl) totalEl.textContent = '$' + total;
            if (daysEl)  daysEl.textContent  = days + ' day(s) × $' + pricePerDay + '/day';
        } else {
            if (totalEl) totalEl.textContent = '$0.00';
            if (daysEl)  daysEl.textContent  = 'Select valid dates to calculate';
        }
    }

    startInput.addEventListener('change', function () {
        // End date cannot be before start date
        endInput.min = startInput.value;
        calculate();
    });

    endInput.addEventListener('change', calculate);
}

/* ============================================
   CONFIRM DIALOGS
   Used in: delete / cancel buttons
   ============================================ */

function confirmDelete(message) {
    return confirm(message || 'Are you sure you want to delete this?');
}

function confirmCancel() {
    return confirm('Are you sure you want to cancel this booking?');
}

/* ============================================
   STATUS FORM AUTO-STYLE
   Highlights the status dropdown based on value
   ============================================ */

function styleStatusSelects() {
    const selects = document.querySelectorAll('.status-select');
    selects.forEach(function (sel) {
        applyStatusStyle(sel);
        sel.addEventListener('change', function () {
            applyStatusStyle(sel);
        });
    });
}

function applyStatusStyle(select) {
    const val = select.value;
    select.className = 'status-select';

    const colorMap = {
        'PENDING':   'color:#A16207; border-color:#FDE68A; background:#FEFCE8;',
        'CONFIRMED': 'color:#166534; border-color:#86EFAC; background:#F0FDF4;',
        'CANCELLED': 'color:#991B1B; border-color:#FCA5A5; background:#FFF1F2;',
        'COMPLETED': 'color:#1E40AF; border-color:#93C5FD; background:#EFF6FF;'
    };

    if (colorMap[val]) {
        select.style.cssText = colorMap[val];
    } else {
        select.style.cssText = '';
    }
}

/* ============================================
   AUTO DISMISS ALERTS
   Dismisses success/error alerts after 4 seconds
   ============================================ */

function autoDismissAlerts() {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function (alert) {
        setTimeout(function () {
            alert.style.transition = 'opacity 0.5s';
            alert.style.opacity = '0';
            setTimeout(function () {
                alert.remove();
            }, 500);
        }, 4000);
    });
}

/* ============================================
   ACTIVE NAV HIGHLIGHT
   Adds 'active' class to current nav link
   ============================================ */

function highlightActiveNav() {
    const currentPath = window.location.pathname;
    const navLinks    = document.querySelectorAll('.topnav-links a, .sidebar-nav .nav-item');

    navLinks.forEach(function (link) {
        const href = link.getAttribute('href');
        if (href && currentPath === href) {
            link.classList.add('active');
        } else if (href && href !== '/' && currentPath.startsWith(href)) {
            link.classList.add('active');
        }
    });
}

/* ============================================
   TABLE SEARCH FILTER
   Filters table rows in real time
   Usage: add id="tableSearch" to a text input
          and class="searchable-table" to your <table>
   ============================================ */

function initTableSearch() {
    const searchInput = document.getElementById('tableSearch');
    const table       = document.querySelector('.searchable-table');

    if (!searchInput || !table) return;

    searchInput.addEventListener('input', function () {
        const query = searchInput.value.toLowerCase().trim();
        const rows  = table.querySelectorAll('tbody tr');

        rows.forEach(function (row) {
            const text = row.textContent.toLowerCase();
            row.style.display = text.includes(query) ? '' : 'none';
        });
    });
}

/* ============================================
   RUN ON PAGE LOAD
   ============================================ */

document.addEventListener('DOMContentLoaded', function () {
    styleStatusSelects();
    autoDismissAlerts();
    highlightActiveNav();
    initTableSearch();
});