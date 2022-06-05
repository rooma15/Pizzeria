function check(event){
    event.preventDefault();
    let req = new XMLHttpRequest();
    req.onreadystatechange = function(){
       if(this.readyState === XMLHttpRequest.DONE && this.status === 403){
           alert("plase fill address and credit card before submitting order");
       }
       if(this.readyState === XMLHttpRequest.DONE && this.status === 200){
           window.location = "/home";
       }
    }
    req.open("post", "/order/submit", true);
    req.send();
}
window.onload = function(){
    document.getElementById("submit-order").addEventListener('submit', check, false);
}