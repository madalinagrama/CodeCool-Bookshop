let button = document.querySelectorAll(".btn-success");
let cart = document.querySelector(".bi-cart");

console.log("ceva");
//
// async function loadData(e){
//     e.preventDefault();
//
//     // let request = await fetch(e.target.href, {
//     //     method: "GET",
//     //     headers: {"Content-Type" : "application/json"}
//     // });
//
//     // let request = await fetch(e.target.href);
//     let API = "/cart?productId=" + e.target.dataset.id;
//     console.log(e.target);
//     let request = await fetch(API);
//     // console.log("request" + request)
//     let response = await request.json();
//
//     console.log("response" + response);
//     console.log("intra in functie")
//
// }
//
// button.forEach(btn => {
//     btn.addEventListener("click",loadData)
// });

const cartPage = (() => { open( "/cart", "_self"); });
cart.addEventListener("click",cartPage);