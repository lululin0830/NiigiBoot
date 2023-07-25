const backToPreviousModalBtn = document.getElementById("backToPreviousModal");

        //Niigi登入回到選擇登入
        backToPreviousModalBtn.addEventListener("click", function () {
            const currentModal = document.getElementById("login_Niigi");
            const previousModal = document.getElementById("login_popup");

            currentModal.style.display = "none";
            previousModal.style.display = "block";

            // 重新打開上一個彈窗
            const previousModalInstance = new bootstrap.Modal(previousModal);
            previousModalInstance.show();

            // 關閉當前彈窗
            const currentModalInstance = bootstrap.Modal.getInstance(currentModal);
            currentModalInstance.hide();
        });
        //忘記密碼回到Niigi登入
        const backToPreviousModalBtn2 = document.getElementById("backToPreviousModal-2");
        backToPreviousModalBtn2.addEventListener("click", function () {
            const currentModal = document.getElementById("forget_password");
            const previousModal = document.getElementById("login_Niigi");

            currentModal.style.display = "none";
            previousModal.style.display = "block";

            // 重新打開上一個彈窗
            const previousModalInstance = new bootstrap.Modal(previousModal);
            previousModalInstance.show();

            // 關閉當前彈窗
            const currentModalInstance = bootstrap.Modal.getInstance(currentModal);
            currentModalInstance.hide();
        });
        //重寄認證信回到Niigi登入
        const backToPreviousModalBtn3 = document.getElementById("backToPreviousModal-3");
        backToPreviousModalBtn3.addEventListener("click", function () {
            const currentModal = document.getElementById("resend_letter");
            const previousModal = document.getElementById("login_Niigi");

            currentModal.style.display = "none";
            previousModal.style.display = "block";

            // 重新打開上一個彈窗
            const previousModalInstance = new bootstrap.Modal(previousModal);
            previousModalInstance.show();

            // 關閉當前彈窗
            const currentModalInstance = bootstrap.Modal.getInstance(currentModal);
            currentModalInstance.hide();
        });
        //註冊回到Niigi登入
        const backToPreviousModalBtn4 = document.getElementById("backToPreviousModal-4");
        backToPreviousModalBtn4.addEventListener("click", function () {
            const currentModal = document.getElementById("register_account");
            const previousModal = document.getElementById("login_Niigi");

            currentModal.style.display = "none";
            previousModal.style.display = "block";

            // 重新打開上一個彈窗
            const previousModalInstance = new bootstrap.Modal(previousModal);
            previousModalInstance.show();

            // 關閉當前彈窗
            const currentModalInstance = bootstrap.Modal.getInstance(currentModal);
            currentModalInstance.hide();
        });