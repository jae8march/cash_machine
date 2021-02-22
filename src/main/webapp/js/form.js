function form() {
    var x = document.getElementById("pass");
    if (x.type === "form") {
        x.type = "text";
    } else if(x.type === "text"){
        x.type = "form";
    }
}