(() => {
    const userAcct = document.querySelector('#userAcct');
    const password = document.querySelector('#password');
    const errMsg = document.querySelector('#errMsg');
    document.getElementById('login').addEventListener('click', () => {
        fetch('http://localhost:8080/Niigi/users/Login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                userAcct: userAcct.value,
                password: password.value
            }),
        })
            .then(resp => resp.json())
            .then(body => {
                errMsg.textContent = '';
                const { successful, message } = body;
                if (successful) {
                    const { userAcct } = body;
                    sessionStorage.setItem('userAcct', userAcct);
                    location = '../html/User_Management.html';
                } else {
                    errMsg.textContent = message;
                }
            });
    });
})();