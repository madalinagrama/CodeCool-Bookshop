let button = document.querySelectorAll(".btn-success");
let cart = document.querySelector(".bi-cart");

async function loadData(e){
    e.preventDefault();

    let request = await fetch(e.target.href, {
        method: "GET",
        headers: {"Content-Type" : "application/json"}
    });
    let response = await request.json();

    console.log(response);

}

button.forEach(btn => {
    btn.addEventListener("click",loadData)
});

const cartPage = (() => { open( "/cart", "_self"); });
cart.addEventListener("click",cartPage);