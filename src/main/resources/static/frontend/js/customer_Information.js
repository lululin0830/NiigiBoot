// 選擇圖片時顯示預覽圖片
var profilePictureInput = document.getElementById('file-upload');
var previewImage = document.getElementById('memPhoto');

profilePictureInput.addEventListener('change', function (event) {
    var file = event.target.files[0];
    var reader = new FileReader();

    reader.onload = function (e) {
        previewImage.src = e.target.result;
    };

    reader.readAsDataURL(file);
});

// 檢查選擇圖片的檔案大小
profilePictureInput.addEventListener('change', function (event) {
    var file = event.target.files[0];
    var maxSize = parseInt(profilePictureInput.getAttribute('max-size'));

    if (file && file.size > maxSize) {
        alert('檔案大小超過限制，請選擇小於1MB的檔案。');
        profilePictureInput.value = '';
        previewImage.src = '#';
    }
});



(() => {
    const memberIdElement = document.querySelector('.memberId');

    fetch(`http://localhost:8080/Niigi/member/selectId?memberId=${encodeURIComponent(memberIdElement.textContent)}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(r => r.json())
    .then(data => {
        const memberAcct = document.querySelector('#memberAcct');
        const name = document.querySelector('#name');
        const phoneInput = document.querySelector('#phone');
        const backupEmail = document.querySelector('#backupEmail');
        const genderInput = document.querySelector('#gender-male');
        const birthday = document.querySelector('#birthday');
        const memPhoto = document.querySelector('#memPhoto');

        if (memberAcct) memberAcct.value = data.memberAcct || '';
        if (name) name.value = data.name || '';
        if (phoneInput) phoneInput.value = data.phone || '';
        if (backupEmail) backupEmail.value = data.backupEmail || '';
        if (genderInput) {
            if (data.gender === '0') {
                genderInput.value = '男';
            } else if (data.gender === '1') {
                genderInput.value = '女';
            } else {
                genderInput.value = ''; // Handle other cases
            }
        }
        if (birthday) birthday.value = data.birthday || '';
        // 设置头像图片
        if (memPhoto) {
            if (data.memPhoto) {
                memPhoto.src = data.memPhoto;
            } else {
                memPhoto.src = './image/product.svg'; // 默认头像路径
            }
        }
    })
    .catch(error => {
        console.error('Error fetching data:', error);
    });
})();







