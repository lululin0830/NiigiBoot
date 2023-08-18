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

function init() {
    const memberIdElement = document.querySelector('.memberId').innerText;
    console.log(memberIdElement)

    fetch('../member/selectId?memberId=' + `${memberId}` , {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwtToken}`
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
            // 設置頭像圖片
            if (memPhoto) {
                if (data.memPhoto) {
                    memPhoto.src = `data:image/jpeg;base64, ${data.memPhoto}`;
                } else {
                    memPhoto.src = './image/product.svg'; // 默認頭像路徑
                }
            }
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
};

(() => {

    // 驗證信箱格式的正則表達式
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    // 驗證手機號碼格式的正則表達式
    const phoneRegex = /^09\d{2}-\d{3}-\d{3}$/;
    const updateMemberInfo = (memberId, name, phone, backupEmail, memPhoto) => {
        // 驗證信箱格式
        if (backupEmail && !emailRegex.test(backupEmail)) {
            alert('請輸入有效的信箱地址。');
            return;
        }

        // 驗證手機號碼格式
        if (!phoneRegex.test(phone)) {
            alert('請輸入有效的手機號碼，格式為 09xx-xxx-xxx。');
            return;
        }
        const formData = new FormData();
        formData.append('memberId', memberId);
        formData.append('name', name);
        formData.append('phone', phone);
        formData.append('backupEmail', backupEmail);
        if (memPhoto) {
            formData.append('memPhoto', memPhoto);
        }

        return fetch('../member/update', {
            method: 'POST',
            body: formData,
            headers: {
            'Authorization': `Bearer ${jwtToken}`
        }
            
        });
    };

    document.getElementById('updateButton').addEventListener('click', () => {
        const memberId = document.querySelector('.memberId').textContent;
        const name = document.querySelector('#name').value;
        const phone = document.querySelector('#phone').value;
        const backupEmail = document.querySelector('#backupEmail').value;
        const memPhotoInput = document.querySelector('#file-upload');

        const memPhoto = memPhotoInput.files[0];

        updateMemberInfo(memberId, name, phone, backupEmail, memPhoto)
            .then(response => {
                if (response.ok) {
                    alert("儲存成功");
                    location.reload(); // 重新載入頁面
                } else {
                    alert("修改失敗");
                }
            })
            .catch(error => {
                console.error('Error updating member info:', error);
            });
    });
})();



window.addEventListener("load",init)