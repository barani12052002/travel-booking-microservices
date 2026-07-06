document.addEventListener("DOMContentLoaded", () => {

    const slots = document.querySelectorAll("input[name='time']");

    slots.forEach(slot => {

        slot.addEventListener("change", calculatePrice);

    });

});

async function calculatePrice() {

    const adults =
        Number(document.getElementById("adultCount").value);

    const children =
        Number(document.getElementById("childCount").value);

    const selectedTime =
        document.querySelector("input[name='time']:checked").value;

    const response = await fetch("/price", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({

            adultCount: adults,

            childCount: children

        })

    });

    const price = await response.json();

    document.getElementById("priceCard").style.display = "block";

    document.getElementById("adultPrice").innerHTML =
        price.adultPrice;

    document.getElementById("childPrice").innerHTML =
        price.childPrice;

    document.getElementById("tax").innerHTML =
        price.tax;

    document.getElementById("serviceCharge").innerHTML =
        price.serviceCharge;

    document.getElementById("discount").innerHTML =
        price.discount;

    document.getElementById("totalPrice").innerHTML =
        price.totalPrice;

    document.getElementById("hiddenTotal").value =
        price.totalPrice;

    document.getElementById("hiddenCurrency").value =
        price.currency;

    document.getElementById("hiddenTime").value =
        selectedTime;

}