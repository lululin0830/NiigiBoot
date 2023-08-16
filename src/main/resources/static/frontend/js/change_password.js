function init(){
    const changePasswordForm = document.getElementById('changePasswordForm');
    const memberId = document.querySelector('.memberId').textContent;
  
    changePasswordForm.addEventListener('submit', async (event) => {
      event.preventDefault();
  
      const oldPassword = document.getElementById('oldPassword').value;
      const newPassword = document.getElementById('newPassword').value;
      const confirmPassword = document.getElementById('confirmPassword').value;
  
      if (newPassword !== confirmPassword) {
        alert('新密碼和確認新密碼不相符');
        return;
      }
      
      if (oldPassword === newPassword) {
        alert('目前密碼和新密碼相同，請輸入不同的新密碼');
        return;
      }

      try {
        const response = await fetch('http://localhost:8080/Niigi/member/changePassword', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          },
          body: `memberId=${encodeURIComponent(memberId)}&oldPassword=${encodeURIComponent(oldPassword)}&newPassword=${encodeURIComponent(newPassword)}`,
        });
  
        const data = await response.text();
  
        if (response.ok) {
          alert(data); // 密碼更改成功
          clearForm(); // 清空表單欄位
        } else {
          alert(data); // 密碼更改失敗，顯示錯誤訊息
        }
      } catch (error) {
        console.error('錯誤:', error);
        alert('發生錯誤。請稍後再試。');
      }
    });
  
    function clearForm() {
      document.getElementById('oldPassword').value = '';
      document.getElementById('newPassword').value = '';
      document.getElementById('confirmPassword').value = '';
    }
  };
  
window.addEventListener("load",init)
  