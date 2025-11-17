/* ========================================
   BILLING PAGE — AUTO UPDATE RATE & PRICE
   ======================================== */

document.addEventListener("DOMContentLoaded", () => {

    const medicineSelect = document.getElementById("selMed");
    const qtyInput = document.querySelector("input[name='quantity']");

    if (medicineSelect && qtyInput) {
        medicineSelect.addEventListener("change", () => {
            const price = medicineSelect.selectedOptions[0].getAttribute("data-price");
            document.getElementById("rateBox").innerText = "₹" + price;
        });

        qtyInput.addEventListener("change", () => {
            const price = medicineSelect.selectedOptions[0].getAttribute("data-price");
            const subtotal = qtyInput.value * price;
            document.getElementById("subtotalBox").innerText = "₹" + subtotal;
        });
    }

});

/* ========================================
   FORM VALIDATION (LIGHTWEIGHT)
   ======================================== */

function validateEmail(email) {
    return /^[A-Za-z0-9._%+-]+@(.+)$/.test(email);
}

function validateNotEmpty(fieldId, message) {
    let el = document.getElementById(fieldId);
    if (!el.value.trim()) {
        alert(message);
        el.focus();
        return false;
    }
    return true;
}

/* ========================================
   CONFIRM DELETE
   ======================================== */

function confirmDelete() {
    return confirm("Are you sure you want to delete this record?");
}

/* ========================================
   HIGHLIGHT ACTIVE NAV ITEM
   ======================================== */

document.querySelectorAll("nav a").forEach(link => {
    if (link.href === window.location.href) {
        link.classList.add("active");
    }
});
