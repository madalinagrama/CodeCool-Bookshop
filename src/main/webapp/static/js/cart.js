let button = document.querySelectorAll(".btn-success");
let cart = document.querySelector(".bi-cart");

async function loadData(e){
    e.preventDefault();

    // let request = await fetch(e.target.href, {
    //     method: "GET",
    //     headers: {"Content-Type" : "application/json"}
    // });

    // let request = await fetch(e.target.href);
    let API = "/cart?productId=" + e.target.dataset.id;
    let request = await fetch(API);
    console.log(request)
    let response = await request.json();

    console.log(response);
    console.log("intra in functie")

}

button.forEach(btn => {
    btn.addEventListener("click",loadData)
});

const cartPage = (() => { open( "/cart", "_self"); });
cart.addEventListener("click",cartPage);