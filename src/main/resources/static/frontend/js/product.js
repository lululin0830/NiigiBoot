// 數量加總
function increaseQuantity() {
    var quantityInput = document.getElementById('quantity');
    quantityInput.value = parseInt(quantityInput.value) + 1;
}

function decreaseQuantity() {
    var quantityInput = document.getElementById('quantity');
    var currentValue = parseInt(quantityInput.value);
    if (currentValue > 1) {
        quantityInput.value = currentValue - 1;
    }
}


//大小圖更換
//javascipt寫法
/*
function change_img(e) {
    var newSrc = e.target.getAttribute("src");
    document.querySelector("#show_L > img").setAttribute("src", newSrc);
}
function init() {
    document.querySelectorAll(".all_img > img").forEach(function(element) {
        element.addEventListener("click", change_img);
    });
}
window.addEventListener('load', init);
*/

//大小圖更換
//ajax寫法
$(document).ready(function() {
    $(".small_img").click(function() {
        var newSrc = $(this).attr("src");
        $("#show_L img").attr("src", newSrc);
    });
});


//關注鈕
$(".join_store").click(function(){
    if ($(this).text() == "加入關注"){
        $(this).text("關注");
    }else {
        $(this).text("加入關注");
    }
})