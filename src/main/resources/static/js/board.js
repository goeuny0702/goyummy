document.querySelectorAll('.faq-question').forEach((item) => {
    item.addEventListener('click', () => {
        const parent = item.parentElement;
        // 다른 활성화된 항목들을 닫음
        document.querySelectorAll('.faq-item').forEach((child) => {
            if (child !== parent) {
                child.classList.remove('active');
            }
        });
        // 클릭된 항목의 활성화 상태를 토글
        parent.classList.toggle('active');
    });
});
